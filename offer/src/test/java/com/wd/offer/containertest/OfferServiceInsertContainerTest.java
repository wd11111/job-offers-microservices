package com.wd.offer.containertest;

import com.wd.clients.offer.OfferDto;
import com.wd.offer.OfferApplication;
import com.wd.offer.Samples;
import com.wd.offer.model.Offer;
import com.wd.offer.repository.OfferRepository;
import com.wd.offer.service.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.wd.offer.containertest.Config.DB_PORT;
import static com.wd.offer.containertest.Config.DOCKER_IMAGE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6BDDAssertions.then;

@SpringBootTest(classes = OfferServiceInsertContainerTest.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceInsertContainerTest implements Samples {

    @Autowired
    OfferService offerService;

    @Autowired
    OfferRepository offerRepository;

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer(DOCKER_IMAGE_NAME);

    static {
        DB_CONTAINER.start();
        System.setProperty(DB_PORT, String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_correctly_add_offer_to_db() {
        OfferDto offerToAdd = sampleOfferDto3();
        String urlOfOffer = offerToAdd.offerUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isFalse();

        OfferDto addedOffer = offerService.saveOffer(offerToAdd);
        Offer actualOffer = offerRepository.findByOfferUrl(addedOffer.offerUrl());

        assertThat(actualOffer).usingRecursiveComparison().ignoringFields("id").isEqualTo(addedOffer);
    }

    @Import(OfferApplication.class)
    static class TestConfig {
    }
}
