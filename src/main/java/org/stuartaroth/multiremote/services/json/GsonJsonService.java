package org.stuartaroth.multiremote.services.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonJsonService implements JsonService {
    private Gson gson;

    public GsonJsonService() {
        this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    }

    @Override
    public String writeJson(Object object) throws Exception {
        return gson.toJson(object);
    }
}
