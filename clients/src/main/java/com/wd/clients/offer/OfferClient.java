package com.wd.clients.offer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "offer", path = "api/v1/offers")
public interface OfferClient {

    @GetMapping
    ResponseEntity<Page<OfferDto>> getOfferList(@RequestParam int page, @RequestParam String sortBy, @RequestParam String sortDir);

    @PostMapping("/list")
    ResponseEntity<List<OfferDto>> saveListOfOffers(@RequestBody List<OfferDto> offers);
}
