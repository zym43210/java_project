package com.example.demo.service;

import com.example.demo.aspect.Loggable;
import com.example.demo.model.ConcertEntity;
import com.example.demo.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository repository;

    @Loggable
    public List getAll() {
        return (List) repository.findAll();
    }

    @Loggable
    public void save(ConcertEntity concertModel) {
        repository.save(concertModel);
    }
}
