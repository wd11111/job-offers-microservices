package com.wd.scheduler;

import com.wd.clients.httpclient.HttpClient;
import com.wd.clients.offer.OfferClient;
import com.wd.clients.offer.OfferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpOfferScheduler {

    private final HttpClient httpClient;
    private final OfferClient offerClient;

    @Scheduled(fixedDelayString = "${delay.hours:PT3H}")
    public void saveOffersFromHttpService() {
        final List<OfferDto> offersFromClient = httpClient.downloadOffers();
        final List<OfferDto> savedOffers = offerClient.saveListOfOffers(offersFromClient).getBody();
        log.info("Added {} offers to database", savedOffers.size());
    }
}
