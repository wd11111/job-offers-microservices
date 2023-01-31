package com.wd.offer.service;

import com.google.common.base.Strings;
import com.wd.offer.exception.OfferDuplicateException;
import com.wd.offer.exception.OfferNotFoundException;
import com.wd.offer.model.Offer;
import com.wd.offer.model.OfferDto;
import com.wd.offer.repository.OfferRepository;
import com.wd.offer.util.OfferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.wd.offer.util.OfferMapper.mapToListOfOffers;
import static com.wd.offer.util.OfferMapper.mapToOffer;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    @Cacheable(value = "offers")
    public List<OfferDto> findAll(int page, String field, String sortDir) {
        Pageable pageable = getPageable(page, field, sortDir);
        return offerRepository.findAll(pageable).stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @CacheEvict(value = "offers", allEntries = true)
    public List<OfferDto> saveListOfOffers(List<OfferDto> offers) {
        List<OfferDto> filteredOffers = filterOffers(offers);
        offerRepository.saveAll(mapToListOfOffers(filteredOffers));
        return filteredOffers;
    }

    @CacheEvict(value = "offers", allEntries = true)
    public OfferDto saveOffer(OfferDto offerDto) {
        Offer offerToInsert = mapToOffer(offerDto);
        try {
            offerRepository.save(offerToInsert);
            return offerDto;
        } catch (DuplicateKeyException e) {
            throw new OfferDuplicateException();
        }
    }

    private List<OfferDto> filterOffers(List<OfferDto> offers) {
        return offers.stream()
                .filter(offer -> !Strings.isNullOrEmpty(offer.offerUrl()))
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.offerUrl()))
                .collect(Collectors.toList());
    }

    private Pageable getPageable(int page, String field, String sortDir) {
        return PageRequest.of(page - 1, 5,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(field).ascending() : Sort.by(field).descending());
    }

}
