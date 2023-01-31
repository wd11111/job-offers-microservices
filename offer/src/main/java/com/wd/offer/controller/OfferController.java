package com.wd.offer.controller;

import com.wd.clients.offer.OfferDto;
import com.wd.offer.service.OfferService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/offers")
@RequiredArgsConstructor
@Validated
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    ResponseEntity<Page<OfferDto>> getOfferList(@RequestParam(defaultValue = "1", required = false) int page,
                                                @RequestParam(defaultValue = "title", required = false) String sortBy,
                                                @RequestParam(defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(new PageImpl<>(offerService.findAll(page, sortBy, sortDir)));
    }

    @GetMapping("/{id}")
    ResponseEntity<OfferDto> getOfferByID(@PathVariable String id) {
        return ResponseEntity.ok(offerService.findById(id));
    }

    @PostMapping()
    ResponseEntity<OfferDto> addOffer(@RequestBody @Valid OfferDto offerDto) {
        return new ResponseEntity<>(offerService.saveOffer(offerDto), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    ResponseEntity<List<OfferDto>> saveListOfOffers(@RequestBody @NotEmpty List<OfferDto> offers){
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.saveListOfOffers(offers));
    }
}