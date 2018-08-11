package org.stuartaroth.multiremote.handlers;

import spark.Request;
import spark.Response;
import spark.Route;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class GetRemotesHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.type(APPLICATION_JSON);
        return "[]";
    }
}
