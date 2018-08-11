package org.stuartaroth.multiremote.services.http;

import java.util.List;
import java.util.Map;

public interface HttpRequest {
    String getMethod();
    HttpRequest setMethod(String method);

    String getUrl();
    HttpRequest setUrl(String url);

    String getContentType();
    HttpRequest setContentType(String contentType);

    String getBody();
    HttpRequest setBody(String body);

    Map<String, List<String>> getQuery();
    String getQueryString();
    HttpRequest addQueryParam(String key, String value);

    Map<String, String> getHeaders();
    HttpRequest addHeader(String key, String value);
}
