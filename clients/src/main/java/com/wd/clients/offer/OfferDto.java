package com.wd.clients.offer;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OfferDto(
        String title,
        String company,
        String salary,
        String offerUrl)
        implements Serializable {
}
