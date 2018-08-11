package org.stuartaroth.multiremote.services.http;

import java.util.Map;

public class DefaultHttpResponse implements HttpResponse {
    private Integer statusCode;
    private String contentType;
    private String body;
    private Map<String, String> headers;

    public DefaultHttpResponse(Integer statusCode, String contentType, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
        this.headers = headers;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}
