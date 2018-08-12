package org.stuartaroth.multiremote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.handlers.GetCommandHandler;
import org.stuartaroth.multiremote.handlers.GetRemotesHandler;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.roku.RokuRemote;
import org.stuartaroth.multiremote.services.config.ConfigService;
import org.stuartaroth.multiremote.services.config.DefaultConfigService;
import org.stuartaroth.multiremote.services.http.*;
import org.stuartaroth.multiremote.services.json.GsonJsonService;
import org.stuartaroth.multiremote.services.json.JsonService;
import org.stuartaroth.multiremote.services.remote.DefaultRemoteService;
import org.stuartaroth.multiremote.services.remote.RemoteService;
import spark.Route;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        ConfigService configService = new DefaultConfigService();

        JsonService jsonService = new GsonJsonService();
        HttpService httpService = new ApacheHttpService();
        RemoteService remoteService = new DefaultRemoteService(httpService);

        port(configService.getHttpPort());

        Route getRemotesHandler = new GetRemotesHandler(jsonService, remoteService);
        Route getCommandHandler = new GetCommandHandler(remoteService);

        staticFiles.location("/client/public");
        get("/api/remotes", getRemotesHandler);
        get("/api/command", getCommandHandler);
    }
}
