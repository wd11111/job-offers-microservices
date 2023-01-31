package com.wd.offer.repository;

import com.wd.offer.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {

    boolean existsByOfferUrl(String offerUrl);

    Offer findByOfferUrl(String offerUrl);
}
