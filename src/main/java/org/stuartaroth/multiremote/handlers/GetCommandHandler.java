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

    public GetCommandHandler(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            Map<String, Remote> uniqueRemotes = remoteService.getRemotes();

            QueryParamsMap queryParamsMap = request.queryMap();
            String remoteKey = queryParamsMap.value("remote");
            String command = queryParamsMap.value("command");
            String text = queryParamsMap.value("text");

            if (text == null) {
                text = "";
            }

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
                case "sendText":
                    remote.sendText(text);
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
