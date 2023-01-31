package com.wd.httpclient.controller;

import com.wd.clients.offer.OfferDto;
import com.wd.httpclient.client.RemoteOfferClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/httpclient")
public class HttpClientController {

    private final RemoteOfferClient offerClient;

    @GetMapping("/offers")
    public List<OfferDto> downloadOffers() {
        return offerClient.getOffers();
    }
}
