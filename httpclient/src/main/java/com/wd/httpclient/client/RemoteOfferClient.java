package com.wd.httpclient.client;

import com.wd.clients.offer.OfferDto;

import java.util.List;

public interface RemoteOfferClient {

    List<OfferDto> getOffers();
}
