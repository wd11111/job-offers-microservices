package com.wd.httpclient;

import com.wd.httpclient.client.RemoteOfferClient;
import com.wd.httpclient.config.OfferClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OfferHttpClientTestConfig extends OfferClientConfig {

    public RemoteOfferClient remoteOfferTestClient(String host, int port, String path, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return offerClient(restTemplate, host, port, path);
    }

}

