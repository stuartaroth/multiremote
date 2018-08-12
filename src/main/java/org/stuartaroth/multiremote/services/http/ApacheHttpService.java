package org.stuartaroth.multiremote.services.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.stuartaroth.multiremote.constants.HttpConstants.*;

public class ApacheHttpService implements HttpService {
    private static Logger logger = LoggerFactory.getLogger(ApacheHttpService.class);

    private HttpClient httpClient;

    public ApacheHttpService() {
        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public HttpResponse makeRequest(HttpRequest httpRequest) throws Exception {
        org.apache.http.client.methods.HttpUriRequest apacheHttpRequest = null;

        String url = httpRequest.getUrl() + httpRequest.getQueryString();

        switch (httpRequest.getMethod().toUpperCase()) {
            case GET:
                apacheHttpRequest = new HttpGet(url);
                break;
            case POST:
                apacheHttpRequest = new HttpPost(url);
                break;
            default:
                throw new Exception("Unrecognized HTTP method");
        }

        for (Map.Entry<String, String> entry : httpRequest.getHeaders().entrySet()) {
            apacheHttpRequest.addHeader(entry.getKey(), entry.getValue());
        }

        org.apache.http.HttpResponse apacheHttpResponse = httpClient.execute(apacheHttpRequest);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(apacheHttpResponse.getEntity().getContent())
        );

        Integer statusCode = apacheHttpResponse.getStatusLine().getStatusCode();
        String contentType = apacheHttpResponse.getFirstHeader(CONTENT_TYPE).getValue();
        String body = bufferedReader.lines().collect(Collectors.joining());
        Map<String, String> headers = new HashMap<>();

        return new DefaultHttpResponse(statusCode, contentType, body, headers);
    }
}
