package com.example.demo.service.impl;

import com.example.demo.aspect.Loggable;
import com.example.demo.config.Mapper;
import com.example.demo.dto.ConcertDto;
import com.example.demo.entity.Concert;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ConcertRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcertServiceImpl implements ConcertService {
    private final ConcertRepository concertRepository;
    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ConcertServiceImpl(ConcertRepository concertRepository,
                              PlaceRepository placeRepository,
                              CommentRepository commentRepository) {
        this.concertRepository = concertRepository;
        this.placeRepository = placeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Loggable
    public Collection<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    @Override
    @Loggable
    public Concert getConcertById(Long id) throws Exception {
        if(concertRepository
                .findById(id)
                .isEmpty()) throw new Exception("Concert not found");
        return concertRepository
                .findById(id)
                .get();
    }

    @Override
    @Loggable
    public Concert getConcertByName(String name) throws Exception {
        if(concertRepository
                .findConcertByName(name)
                .isEmpty()) throw new Exception("Concert not found");
        return concertRepository
                .findConcertByName(name)
                .get();
    }

    @Override
    @Loggable
    public void saveConcert(@Valid ConcertDto concertDto) {
        Concert concert = Mapper.map(concertDto, Concert.class);
        concert.setPlace(placeRepository
                .findPlaceByName(concertDto
                        .getPlaceName())
                .get());

        concert.setComments(new HashSet<>());
        concertRepository.save(concert);
    }

    @Override
    @Loggable
    public Collection<Concert> getConcerts(int page, int counter) {
        Pageable pageable = PageRequest.of(page, counter);
        return concertRepository.findAll(pageable).getContent();
    }

    @Override
    @Loggable
    public Collection<Concert> getAllConcertByName(String name, int page, int counter) {
        Pageable pageable = PageRequest.of(page, counter);
        return concertRepository.findAll(pageable)
                .stream()
                .filter(x->x.getName()
                        .contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Concert> getAllConcertByParams(ConcertDto concertDto) {
        return concertRepository.getConcertsByParams(concertDto.getName(),
                concertDto.getPlaceName(),
                concertDto.getStartDate(),
                concertDto.getFinishDate())
                .get();
    }

    @Override
    public void deleteAllConcertsByPlaceId(Long placeId) {
        placeRepository.deletePlaceById(placeId);
    }

    @Override
    public Optional<Collection<Concert>> getAllConcertsByPlaceId(Long placeId) {
        return concertRepository.getConcertsByPlaceId(placeId);
    }

    @Override
    public void deleteConcert(Long id) {
        if(commentRepository.getCommentsByConcertId(id).isPresent()){
            for(var y: commentRepository.getCommentsByConcertId(id).get()){
                commentRepository.deleteCommentsByConcertId(y.getId());
            }
        }
        concertRepository.deleteConcertById(id);
    }

    @Override
    public void updateConcert(ConcertDto concertDto, Long id) {
        Concert concertToUpdate=concertRepository.findById(id).get();
        concertToUpdate.setName(concertDto.getName());
        concertToUpdate.setStartDate(concertDto.getStartDate());
        concertToUpdate.setFinishDate(concertDto.getFinishDate());
        concertToUpdate.setPlace(placeRepository.findPlaceByName(concertDto.getPlaceName()).get());
        concertRepository.save(concertToUpdate);
    }
}
