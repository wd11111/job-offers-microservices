package com.wd.httpclient;

import com.wd.clients.offer.OfferDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface SampleOfferResponse extends Samples {

    default ResponseEntity<List<OfferDto>> responseWithOneOffer() {
        return new ResponseEntity<>(Collections.singletonList(sampleOfferDto1()), HttpStatus.ACCEPTED);
    }

    default ResponseEntity<List<OfferDto>> responseWithOffers(OfferDto... offerDto) {
        return new ResponseEntity<>(Arrays.asList(offerDto), HttpStatus.ACCEPTED);
    }


}
