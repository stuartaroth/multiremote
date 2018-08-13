package org.stuartaroth.multiremote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.handlers.GetCommandHandler;
import org.stuartaroth.multiremote.handlers.GetRemotesHandler;
import org.stuartaroth.multiremote.services.config.ConfigService;
import org.stuartaroth.multiremote.services.config.DefaultConfigService;
import org.stuartaroth.multiremote.services.http.*;
import org.stuartaroth.multiremote.services.json.GsonJsonService;
import org.stuartaroth.multiremote.services.json.JsonService;
import org.stuartaroth.multiremote.services.remote.DefaultRemoteService;
import org.stuartaroth.multiremote.services.remote.RemoteService;
import spark.Route;

import static spark.Spark.*;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
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
        } catch (Exception e) {
            logger.error("Exception in main: {}", e);
        }
    }
}
