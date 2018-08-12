interface IUrlService {
    getQueryMap(): { [key:string]:string[]; }
    isRemotePage(): boolean;
}

class UrlService implements IUrlService {
    private queryMap: { [key: string]: string[] } = null;

    private addToQueryMap(key: string, value: string): void {
        if (this.queryMap == null) {
            this.queryMap = {};
        }

        var values = [];
        if (this.queryMap[key]) {
            values = this.queryMap[key];
        }

        values.push(value);

        this.queryMap[key] = values;
    }

    getQueryMap(): { [key: string]: string[] } {
        try {
            if (this.queryMap != null) {
                return this.queryMap;
            }

            var url = window.location.href;
            var questionIndex = url.indexOf("?");
            if (questionIndex == -1) {
                this.queryMap = {};
                return this.queryMap;
            }

            var queryString = url.substring(questionIndex + 1);

            var params = queryString.split("&");
            params.forEach((param: string) => {
                var equalsIndex = param.indexOf("=");
                if (equalsIndex == -1) {
                    this.addToQueryMap(param, "true");
                }

                var keyAndValue = param.split("=");
                if (keyAndValue.length == 2) {
                    this.addToQueryMap(keyAndValue[0], keyAndValue[1]);
                }
            });

            return this.queryMap;
        } catch (e) {
            console.log("UrlService.getQueryMap exception:", e);
            this.queryMap = null;
            return {};
        }
    }

    isRemotePage(): boolean {
        return window.location.href.indexOf("remote.html") > -1;
    }
}

interface IHttpResponse {
    statusCode: number
    body: string
}

interface IHttpService {
    get(url: string, callback: (IHttpResponse) => void): void;
}

class HttpService implements IHttpService {
    get(url: string, callback: (IHttpResponse) => void) {
        var r = new XMLHttpRequest();
        r.open("GET", url, true);
        r.onreadystatechange = function () {
            if (r.readyState != 4) {
                var httpResponse: IHttpResponse = {
                    statusCode: r.status,
                    body: r.responseText
                };
                callback(httpResponse);
            }
        };
        r.send();
    }
}

interface IJsonService {
    readJson(body: string, returnValueIfException: any): any
    writeJson(object: any, returnValueIfException: string): string
}

class JsonService implements IJsonService {
    readJson(body: string, returnValueIfException: any): any {
        try {
            return JSON.parse(body);
        } catch (e) {
            console.log("JsonService.readJson exception:", e);
            return returnValueIfException;
        }
    }

    writeJson(object: any, returnValueIfException: string): string {
        try {
            return JSON.stringify(object);
        } catch (e) {
            console.log("JsonService.writeJson exception:", e);
            return returnValueIfException;
        }
    }
}

interface IRemoteInfo {
    key: string;
    displayName: string;
}

interface IHtmlService {
    getIRemoteInfoHtml(remoteInfo: IRemoteInfo): string;
}

class HtmlService implements IHtmlService {
    getIRemoteInfoHtml(remoteInfo: IRemoteInfo): string {
        return `<div><a href="/remote.html?remote=${remoteInfo.key}">${remoteInfo.displayName}</a></div>`;
    }
}

interface INotificationService {
    notify(message: string): void;
}

class NotificationService implements INotificationService {
    notify(message: string): void {
        //todo change to a better system than alerts
        alert(message);
    }
}

interface IRedirectService {
    redirect(url: string, notify: string): void;
}

class RedirectService implements IRedirectService {
    redirect(url: string, notifyMessage: string): void {
        window.location.href = url + `?notify=${notifyMessage}`;
    }
}

interface IRemoteCommandService {
    setRemoteKey(key: string): void;
    back(): void;
    info(): void;
    home(): void;
    up(): void;
    left(): void;
    enter(): void;
    right(): void;
    down(): void;
    rewind(): void;
    playpause(): void;
    forward(): void;
    mute(): void;
    volumeDown(): void;
    volumeUp(): void;
}

class RemoteCommandService implements IRemoteCommandService {
    private httpService: IHttpService;
    private remoteKey: string;

    constructor(httpService: IHttpService) {
        this.httpService = httpService;
    }

    setRemoteKey(key: string): void {
        this.remoteKey = key;
    }

    private sendCommand(command: string) {
        httpService.get(`api/command?remote=${remoteKey}&command=${command}`, (httpResponse: IHttpResponse) => {
            console.log("sendCommand", remoteKey, command, httpResponse);
        });
    }

    back(): void {
        this.sendCommand("back");
    }

    down(): void {
        this.sendCommand("down");
    }

    enter(): void {
        this.sendCommand("enter");
    }

    forward(): void {
        this.sendCommand("forward");
    }

    home(): void {
        this.sendCommand("home");
    }

    info(): void {
        this.sendCommand("info");
    }

    left(): void {
        this.sendCommand("left");
    }

    mute(): void {
        this.sendCommand("mute");
    }

    playpause(): void {
        this.sendCommand("playpause");
    }

    rewind(): void {
        this.sendCommand("rewind");
    }

    right(): void {
        this.sendCommand("right");
    }

    up(): void {
        this.sendCommand("up");
    }

    volumeDown(): void {
        this.sendCommand("volumeDown");
    }

    volumeUp(): void {
        this.sendCommand("volumeUp");
    }
}

var urlService: IUrlService = new UrlService();
var httpService: IHttpService = new HttpService();
var jsonService: IJsonService = new JsonService();
var htmlService: IHtmlService = new HtmlService();
var notificationService: INotificationService = new NotificationService();
var redirectService: IRedirectService = new RedirectService();
var remoteCommandService: IRemoteCommandService = new RemoteCommandService(httpService);

var remoteInfos: IRemoteInfo[] = [];
var remoteKey: string = "";

var queryMap: { [key: string]: string[] } = urlService.getQueryMap();

if (queryMap["notify"]) {
    var notifyValues = queryMap["notify"];
    var notifyMessage = decodeURIComponent(notifyValues[0]);
    notificationService.notify(notifyMessage);
}

httpService.get("api/remotes", (httpResponse: IHttpResponse) => {
    if (httpResponse.body) {
        remoteInfos = jsonService.readJson(httpResponse.body, []);

        if (!urlService.isRemotePage()) {
            var element = document.getElementById("remotes");
            if (element != null) {
                var htmlStrings: string[] = remoteInfos.map(htmlService.getIRemoteInfoHtml);
                element.innerHTML = htmlStrings.join("");
                return;
            }
        } else {
            var remoteValues = queryMap["remote"];
            if (remoteValues == null || remoteValues.length == 0) {
                redirectService.redirect("/", "You must provide a 'remote' query param");
                return;
            }

            var remoteValue = remoteValues[0];

            var validRemoteValues: string[] = remoteInfos.map((remoteInfo: IRemoteInfo) => remoteInfo.key);
            if (validRemoteValues.indexOf(remoteValue) == -1) {
                redirectService.redirect("/", "You must provide a valid 'remote' query param");
                return;
            }

            remoteKey = remoteValue;
        }
    }
});
