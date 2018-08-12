package org.stuartaroth.multiremote;


import org.stuartaroth.multiremote.handlers.GetRemotesHandler;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.remotes.RokuRemote;
import org.stuartaroth.multiremote.services.config.ConfigService;
import org.stuartaroth.multiremote.services.config.DefaultConfigService;
import org.stuartaroth.multiremote.services.http.*;
import org.stuartaroth.multiremote.services.json.GsonJsonService;
import org.stuartaroth.multiremote.services.json.JsonService;
import spark.Route;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) throws Exception {

        ConfigService configService = new DefaultConfigService();

        JsonService jsonService = new GsonJsonService();
        HttpService httpService = new ApacheHttpService();

        List<Remote> remotes = Arrays.asList(
                new RokuRemote(httpService)
        );

        port(configService.getHttpPort());

        Route getRemotesHandler = new GetRemotesHandler(jsonService, remotes);

        staticFiles.location("/client/public");
        get("/api/remotes", getRemotesHandler);
    }
}
