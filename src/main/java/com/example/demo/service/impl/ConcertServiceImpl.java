package com.example.demo.service.impl;

import com.example.demo.aspect.Loggable;
import com.example.demo.config.Mapper;
import com.example.demo.dto.EventDto;
import com.example.demo.entity.Event;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.service.EventService;
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
public class ConcertServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ConcertServiceImpl(EventRepository eventRepository,
                              PlaceRepository placeRepository,
                              CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.placeRepository = placeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Loggable
    public Collection<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    @Loggable
    public Event getEventById(Long id) throws Exception {
        if(eventRepository
                .findById(id)
                .isEmpty()) throw new Exception("Event not found");
        return eventRepository
                .findById(id)
                .get();
    }

    @Override
    @Loggable
    public Event getEventByName(String name) throws Exception {
        if(eventRepository
                .findEventByName(name)
                .isEmpty()) throw new Exception("Event not found");
        return eventRepository
                .findEventByName(name)
                .get();
    }

    @Override
    @Loggable
    public void saveEvent(@Valid EventDto eventDto) {
        Event event = Mapper.map(eventDto, Event.class);
        event.setPlace(placeRepository
                .findPlaceByName(eventDto
                        .getPlaceName())
                .get());

        event.setComments(new HashSet<>());
        eventRepository.save(event);
    }

    @Override
    @Loggable
    public Collection<Event> getEvents(int page, int counter) {
        Pageable pageable = PageRequest.of(page, counter);
        return eventRepository.findAll(pageable).getContent();
    }

    @Override
    @Loggable
    public Collection<Event> getAllEventByName(String name, int page, int counter) {
        Pageable pageable = PageRequest.of(page, counter);
        return eventRepository.findAll(pageable)
                .stream()
                .filter(x->x.getName()
                        .contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Event> getAllEventByParams(EventDto eventDto) {
        return eventRepository.getEventsByParams(eventDto.getName(),
                eventDto.getPlaceName(),
                eventDto.getStartDate(),
                eventDto.getFinishDate())
                .get();
    }

    @Override
    public void deleteAllEventsByPlaceId(Long placeId) {
        placeRepository.deletePlaceById(placeId);
    }

    @Override
    public Optional<Collection<Event>> getAllEventsByPlaceId(Long placeId) {
        return eventRepository.getEventsByPlaceId(placeId);
    }

    @Override
    public void deleteEvent(Long id) {
        if(commentRepository.getCommentsByEventId(id).isPresent()){
            for(var y: commentRepository.getCommentsByEventId(id).get()){
                commentRepository.deleteCommentsByEventId(y.getId());
            }
        }
        eventRepository.deleteEventById(id);
    }

    @Override
    public void updateEvent(EventDto eventDto, Long id) {
        Event eventToUpdate=eventRepository.findById(id).get();
        eventToUpdate.setName(eventDto.getName());
        eventToUpdate.setStartDate(eventDto.getStartDate());
        eventToUpdate.setFinishDate(eventDto.getFinishDate());
        eventToUpdate.setPlace(placeRepository.findPlaceByName(eventDto.getPlaceName()).get());
        eventRepository.save(eventToUpdate);
    }
}
