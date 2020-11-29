package com.example.demo.service;

import com.example.demo.dto.ConcertDto;
import com.example.demo.entity.Concert;

import java.util.Collection;
import java.util.Optional;

public interface ConcertService {
    Collection<Concert> getAllConcerts();
    Concert getConcertById(Long id) throws Exception;
    Concert getConcertByName(String name) throws Exception;
    void saveConcert(ConcertDto concertDto);
    Collection<Concert> getConcerts(int page, int counter);
    Collection<Concert> getAllConcertByName(String name, int page, int counter);
    Collection<Concert> getAllConcertByParams(ConcertDto concertDto);
    void deleteAllConcertsByPlaceId(Long placeId);
    Optional<Collection<Concert>> getAllConcertsByPlaceId(Long placeId);
    void deleteConcert(Long id);
    void updateConcert(ConcertDto concertDto, Long id);
}
