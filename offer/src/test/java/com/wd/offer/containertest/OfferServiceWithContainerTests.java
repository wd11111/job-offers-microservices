package com.wd.offer.containertest;

import com.wd.clients.offer.OfferDto;
import com.wd.exceptionhandler.exception.OfferDuplicateException;
import com.wd.exceptionhandler.exception.OfferNotFoundException;
import com.wd.offer.OfferApplication;
import com.wd.offer.Samples;
import com.wd.offer.model.Offer;
import com.wd.offer.repository.OfferRepository;
import com.wd.offer.service.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.wd.offer.containertest.Config.DB_PORT;
import static com.wd.offer.containertest.Config.DOCKER_IMAGE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = OfferServiceWithContainerTests.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
class OfferServiceWithContainerTests implements Samples {

    private static final String WRONG_ID = "wrong_id";

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
    void should_return_list_of_all_offers() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        List<Offer> offersFromRepo = List.of(sampleOffer1(), sampleOffer2());
        List<OfferDto> expectedOffers = List.of(sampleOfferDto1(), sampleOfferDto2());
        then(offerRepository.findAll(pageable)).containsExactlyInAnyOrderElementsOf(new PageImpl<>(offersFromRepo));

        List<OfferDto> allOffers = offerService.findAll(1, "name", "asc");

        assertThat(allOffers).containsAll(expectedOffers);
    }

    @Test
    void should_return_correct_offer() {
        then(offerRepository.findById("63223dcb1a420777c05ffd79")).isPresent();

        OfferDto offerById = offerService.findById("63223dcb1a420777c05ffd79");

        assertThat(offerById).isEqualTo(sampleOfferDto1());
    }

    @Test
    void should_throw_offer_not_found_exception() {
        then(offerRepository.findById(WRONG_ID)).isNotPresent();

        assertThatThrownBy(() -> offerService.findById(WRONG_ID)).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", "wrong_id"));
    }

    @Test
    void should_throw_offer_duplicate_exception() {
        OfferDto offerToAdd = sampleOfferDto1();
        String urlOfOffer = offerToAdd.offerUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isTrue();

        assertThatThrownBy(() -> offerService.saveOffer(offerToAdd)).isInstanceOf(OfferDuplicateException.class)
                .hasMessageContaining("Offer with this url already exists");
    }

    @Import(OfferApplication.class)
    static class TestConfig {
    }

}
