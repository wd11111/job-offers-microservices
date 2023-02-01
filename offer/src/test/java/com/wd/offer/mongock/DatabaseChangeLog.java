package com.wd.offer.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.spring.v5.EnableMongock;
import com.wd.offer.model.Offer;
import com.wd.offer.repository.OfferRepository;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ChangeLog
@EnableMongock
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "seedDatabase", author = "wd")
    public void seedDatabase(OfferRepository offerRepository) {
        offerRepository.insert(List.of(sampleOffer1(), sampleOffer2()));
    }

    private Offer sampleOffer1() {
        return new Offer("63223dcb1a420777c05ffd79", "Remote Junior Java Developer",
                "Tutlo sp zoo", "8 000 - 12 000 PLN",
                "https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0");
    }

    private Offer sampleOffer2() {
        return new Offer("63223dcb1a420777c05ffd7a", "Junior Salesforce/Fullstack Developer",
                "Youdigital Sp. z o.o.", "4 500 - 8 500 PLN",
                "https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv");
    }
}
