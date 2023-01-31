package com.wd.offer.exception;

public class OfferDuplicateException extends RuntimeException{

    public OfferDuplicateException() {
        super("Offer with this url already exists");
    }
}
