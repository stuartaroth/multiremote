package org.stuartaroth.multiremote.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stuartaroth.multiremote.remotes.Remote;
import org.stuartaroth.multiremote.services.remote.RemoteService;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

import static org.stuartaroth.multiremote.constants.RemoteConstants.VALID_COMMANDS;

public class GetCommandHandler implements Route {
    private static Logger logger = LoggerFactory.getLogger(GetCommandHandler.class);

    private RemoteService remoteService;
    private Map<String, Remote> uniqueRemotes;

    public GetCommandHandler(RemoteService remoteService) {
        this.remoteService = remoteService;
        this.uniqueRemotes = remoteService.getRemotes();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            QueryParamsMap queryParamsMap = request.queryMap();
            String remoteKey = queryParamsMap.value("remote");
            String command = queryParamsMap.value("command");

            if (remoteKey == null || !uniqueRemotes.containsKey(remoteKey)) {
                response.status(422);
                return "You must provide a valid remote";
            }

            if (command == null || !VALID_COMMANDS.contains(command)) {
                response.status(422);
                return "You must provide a valid command";
            }

            Remote remote = uniqueRemotes.get(remoteKey);

            switch (command) {
                case "back":
                    remote.back();
                    break;
                case "info":
                    remote.info();
                    break;
                case "home":
                    remote.home();
                    break;
                case "up":
                    remote.up();
                    break;
                case "left":
                    remote.left();
                    break;
                case "enter":
                    remote.enter();
                    break;
                case "right":
                    remote.right();
                    break;
                case "down":
                    remote.down();
                    break;
                case "rewind":
                    remote.rewind();
                    break;
                case "playpause":
                    remote.playpause();
                    break;
                case "forward":
                    remote.forward();
                    break;
                case "mute":
                    remote.mute();
                    break;
                case "volumeDown":
                    remote.volumeDown();
                    break;
                case "volumeUp":
                    remote.volumeUp();
                    break;
            }

            response.status(204);
            return "";
        } catch (Exception e) {
            logger.error("exception: {}", e);
            response.status(500);
            return "Something went wrong";
        }
    }
}
