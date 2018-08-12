package org.stuartaroth.multiremote.services.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonJsonService implements JsonService {
    private static Logger logger = LoggerFactory.getLogger(GsonJsonService.class);

    private Gson gson;

    public GsonJsonService() {
        this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    }

    @Override
    public String writeJson(Object object) throws Exception {
        return gson.toJson(object);
    }
}
