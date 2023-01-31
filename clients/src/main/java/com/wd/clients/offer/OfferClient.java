package com.wd.clients.offer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "offer", path = "api/v1/offers")
public interface OfferClient {

    @GetMapping
    ResponseEntity<Page<OfferDto>> getOfferList(int page, String sortBy, String sortDir);
}
