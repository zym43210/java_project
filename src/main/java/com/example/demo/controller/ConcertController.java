package com.example.demo.controller;

import com.example.demo.model.ConcertEntity;
import com.example.demo.service.ConcertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ConcertController {

    @Autowired
    private ConcertService concertService;
    Logger logger = LoggerFactory.getLogger(ConcertController.class);

    @GetMapping("/concerts")
    public ResponseEntity getConcerts() {
        logger.info("do filter...");
        return ResponseEntity.ok(concertService.getAll());
    }


    @PostMapping("/concerts")
    public void saveConcert(@RequestBody ConcertEntity concertModel) {
        logger.info("do save...");
        concertService.save(concertModel);
    }

}
