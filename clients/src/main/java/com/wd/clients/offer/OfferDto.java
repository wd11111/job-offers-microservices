package com.wd.clients.offer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferDto(
        @NotNull(message = "can not be null")
        @NotBlank(message = "can not be empty")
        String title,
        @NotNull(message = "can not be null")
        @NotBlank(message = "can not be empty")
        String company,
        @NotNull(message = "can not be null")
        @NotBlank(message = "can not be empty")
        String salary,
        @NotNull(message = "can not be null")
        @NotBlank(message = "can not be empty")
        String offerUrl) {
}
