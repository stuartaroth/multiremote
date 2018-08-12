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

var urlService: IUrlService = new UrlService();
var httpService: IHttpService = new HttpService();
var jsonService: IJsonService = new JsonService();
var htmlService: IHtmlService = new HtmlService();

var remoteInfos: IRemoteInfo[] = [];

if (!urlService.isRemotePage()) {
    httpService.get("api/remotes", (httpResponse: IHttpResponse) => {
        if (httpResponse.body) {
            remoteInfos = jsonService.readJson(httpResponse.body, []);
            var element = document.getElementById("remotes");
            if (element != null) {
                var htmlStrings: string[] = remoteInfos.map(htmlService.getIRemoteInfoHtml);
                element.innerHTML = htmlStrings.join("");
            }
        }
    });
}

if (urlService.isRemotePage()) {
    
}
