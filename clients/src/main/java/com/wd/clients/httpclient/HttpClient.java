package com.wd.clients.httpclient;

import com.wd.clients.offer.OfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "external-http-client", path = "api/v1/httpclient")
public interface HttpClient {

    @GetMapping("/offers")
    List<OfferDto> downloadOffers();

}
