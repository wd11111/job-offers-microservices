package com.wd.offer.util;


import com.wd.clients.offer.OfferDto;
import com.wd.offer.model.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class OfferMapper {

    public static OfferDto mapToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.getTitle())
                .offerUrl(offer.getOfferUrl())
                .salary(offer.getSalary())
                .company(offer.getCompany())
                .build();
    }

    public static Offer mapToOffer(OfferDto offer) {
        return Offer.builder()
                .title(offer.title())
                .offerUrl(offer.offerUrl())
                .salary(offer.salary())
                .company(offer.company())
                .build();
    }

    public static List<Offer> mapToListOfOffers(List<OfferDto> offers) {
        return offers.stream()
                .map(OfferMapper::mapToOffer)
                .collect(Collectors.toList());
    }

}
