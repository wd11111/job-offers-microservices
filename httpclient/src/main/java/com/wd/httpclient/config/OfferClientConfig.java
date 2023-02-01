package com.wd.httpclient.config;

import com.wd.httpclient.client.OfferHttpClient;
import com.wd.httpclient.client.RemoteOfferClient;
import com.wd.httpclient.error.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferClientConfig {

    @Bean
    protected RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    protected RestTemplate restTemplate(@Value("${offer.http.client.config.connectionTimeout}") long connectionTimeout,
                                        @Value("${offer.http.client.config.readTimeout}") long readTimeout,
                                        RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    protected RemoteOfferClient offerClient(RestTemplate restTemplate,
                                            @Value("${offer.http.client.config.host}") String host,
                                            @Value("${offer.http.client.config.port}") int port,
                                            @Value("${offer.http.client.config.path}") String path) {
        return new OfferHttpClient(restTemplate, host, port, path);
    }

}
