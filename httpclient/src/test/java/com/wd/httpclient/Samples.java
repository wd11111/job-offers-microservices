package com.wd.httpclient;

import com.wd.clients.offer.OfferDto;

public interface Samples {

    default OfferDto sampleOfferDto1() {
        return new OfferDto("Remote Junior Java Developer",
                "Tutlo sp zoo", "8 000 - 12 000 PLN",
                "https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0");
    }

    default OfferDto sampleOfferDto2() {
        return new OfferDto("Junior Salesforce/Fullstack Developer",
                "Youdigital Sp. z o.o.", "4 500 - 8 500 PLN",
                "https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv");
    }
}
