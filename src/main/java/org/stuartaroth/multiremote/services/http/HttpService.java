package org.stuartaroth.multiremote.services.http;

public interface HttpService {
    HttpResponse makeRequest(HttpRequest httpRequest) throws Exception;
}
