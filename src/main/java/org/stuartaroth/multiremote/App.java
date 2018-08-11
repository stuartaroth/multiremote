package org.stuartaroth.multiremote;


import org.stuartaroth.multiremote.handlers.GetRemotesHandler;
import org.stuartaroth.multiremote.services.config.ConfigService;
import org.stuartaroth.multiremote.services.config.DefaultConfigService;
import org.stuartaroth.multiremote.services.http.*;
import spark.Route;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) throws Exception {
        ConfigService configService = new DefaultConfigService();

        HttpService httpService = new ApacheHttpService();

        port(configService.getHttpPort());

        Route getRemotesHandler = new GetRemotesHandler();

        staticFiles.location("/client/public");
        get("/api/remotes", getRemotesHandler);
    }
}
