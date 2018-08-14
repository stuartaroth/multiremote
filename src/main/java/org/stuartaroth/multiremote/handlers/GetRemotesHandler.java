package org.stuartaroth.multiremote.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RemoteInfo;
import org.stuartaroth.multiremote.services.json.JsonService;
import org.stuartaroth.multiremote.services.remote.RemoteService;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class GetRemotesHandler implements Route {
    private static Logger logger = LoggerFactory.getLogger(GetRemotesHandler.class);

    private JsonService jsonService;
    private RemoteService remoteService;

    private List<RemoteInfo> remoteInfos;

    public GetRemotesHandler(JsonService jsonService, RemoteService remoteService) {
        this.jsonService = jsonService;
        this.remoteService = remoteService;
        getRemotes(false);
    }

    private void getRemotes(Boolean rescan) {
        Map<String, Remote> uniqueRemotes = remoteService.getRemotes(rescan);
        remoteInfos = uniqueRemotes.values().stream().map(Remote::getRemoteInfo).sorted(Comparator.comparing(RemoteInfo::getKey)).collect(Collectors.toList());
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        QueryParamsMap queryParamsMap = request.queryMap();
        String rescan = queryParamsMap.value("rescan");
        if (rescan != null) {
            getRemotes(true);
        }

        response.type(APPLICATION_JSON);
        return jsonService.writeJson(remoteInfos);
    }
}
