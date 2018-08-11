package org.stuartaroth.multiremote.services.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class DefaultHttpRequest implements HttpRequest {
    private String method;
    private String url;
    private String body;
    private Map<String, List<String>> query;
    private Map<String, String> headers;

    public DefaultHttpRequest() {
        query = new HashMap<>();
        headers = new HashMap<>();
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public HttpRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    @Override
    public HttpRequest setContentType(String contentType) {
        headers.put(CONTENT_TYPE, contentType);
        return this;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public Map<String, List<String>> getQuery() {
        return query;
    }

    @Override
    public String getQueryString() {
        boolean firstEntry = true;
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : query.entrySet()) {
            for (String value : entry.getValue()) {
                if (firstEntry) {
                    stringBuilder.append("?");
                    firstEntry = false;
                } else {
                    stringBuilder.append("&");
                }

                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(value);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public HttpRequest addQueryParam(String key, String value) {
        List<String> values = query.getOrDefault(key, new ArrayList<>());
        values.add(value);
        query.put(key, values);
        return this;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public HttpRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }
}
