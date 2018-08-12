package org.stuartaroth.multiremote.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RemoteInfo;
import org.stuartaroth.multiremote.services.json.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class GetRemotesHandler implements Route {
    private static Logger logger = LoggerFactory.getLogger(GetRemotesHandler.class);

    private JsonService jsonService;
    private Map<String, Remote> uniqueRemotes;
    private List<RemoteInfo> remoteInfos;

    public GetRemotesHandler(JsonService jsonService, Map<String, Remote> uniqueRemotes) {
        this.jsonService = jsonService;
        this.uniqueRemotes = uniqueRemotes;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (remoteInfos == null) {
            remoteInfos = uniqueRemotes.values().stream().map(Remote::getRemoteInfo).collect(Collectors.toList());
        }

        response.type(APPLICATION_JSON);
        return jsonService.writeJson(remoteInfos);
    }
}
