package org.stuartaroth.multiremote.handlers;

import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RemoteInfo;
import org.stuartaroth.multiremote.services.json.JsonService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.stream.Collectors;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class GetRemotesHandler implements Route {
    private JsonService jsonService;
    private List<Remote> remotes;
    private List<RemoteInfo> remoteInfos;

    public GetRemotesHandler(JsonService jsonService, List<Remote> remotes) {
        this.jsonService = jsonService;
        this.remotes = remotes;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (remoteInfos == null) {
            remoteInfos = remotes.stream().map(Remote::getRemoteInfo).collect(Collectors.toList());
        }

        response.type(APPLICATION_JSON);
        return jsonService.writeJson(remoteInfos);
    }
}
