package org.stuartaroth.multiremote.services.http;

import java.util.Map;

public interface HttpResponse {
    Integer getStatusCode();
    String getContentType();
    String getBody();
    Map<String, String> getHeaders();
}
