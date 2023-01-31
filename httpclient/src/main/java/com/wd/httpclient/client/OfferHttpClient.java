package com.wd.httpclient.client;

import com.wd.clients.offer.OfferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OfferHttpClient implements RemoteOfferClient {

    private final RestTemplate restTemplate;
    private final String host;
    private final int port;
    private final String path;

    @Override
    public List<OfferDto> getOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        String url = host + ":" + port + path;

        try {
            ResponseEntity<List<OfferDto>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<List<OfferDto>>() {
                    });
            final List<OfferDto> body = response.getBody();
            return (body != null) ? body : Collections.emptyList();
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
