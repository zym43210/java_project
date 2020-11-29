package com.example.demo.rest;

import com.example.demo.dto.ConcertDto;
import com.example.demo.entity.Concert;
import com.example.demo.service.impl.ConcertServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@CrossOrigin(value = "*")
@RestController
public class ConcertRestController {
    private final ConcertServiceImpl concertService;

    @Autowired
    public ConcertRestController(ConcertServiceImpl concertService) {
        this.concertService = concertService;
    }

    @Operation(summary = "Get concerts size")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Size returned",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping(value = "/rest/api/v1/concerts/length")
    public ResponseEntity<Integer> getAllConcertsByInput(){
        return new ResponseEntity<>(concertService.getAllConcerts().size(), HttpStatus.OK);
    }

    @Operation(summary = "Find concert by different params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts founded",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concerts not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @GetMapping(value = "/rest/api/v1/concerts/input")
    public ResponseEntity<Collection<Concert>> getAllConcertsByInput(@RequestParam("input") String input, @RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return new ResponseEntity<>(concertService.getAllConcertByName(input, page, size), HttpStatus.OK);
    }

    @Operation(summary = "Get concert by concert id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert founded",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concert not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @GetMapping(value = "/rest/api/v1/concerts/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(concertService.getConcertById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get concerts with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts founded",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concerts not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @GetMapping(value = "/rest/api/v1/concerts")
    public ResponseEntity<Collection<Concert>> getNumberOfConcerts(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return new ResponseEntity<>(concertService.getConcerts(page, size),HttpStatus.OK);
    }

    @Operation(summary = "Add concert", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert added",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
            @ApiResponse(responseCode = "403", description = "Resource forbidden")
    })
    @PostMapping(value = "/rest/api/v1/concerts")
    public ResponseEntity<Concert> saveConcert(@RequestBody @Valid ConcertDto concertDto){
        concertService.saveConcert(concertDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get concerts by different params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts founded",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concerts not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @GetMapping(value = "/rest/api/v1/concerts/params")
    public ResponseEntity<Collection<Concert>> getAllConcertsByParams(@RequestParam(value = "country", required = false)  String country,
                                                                  @RequestParam(value = "place", required = false)  String place,
                                                                  @RequestParam(value = "startDate", required = false)
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                  @RequestParam(value = "finishDate", required = false)
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date finishDate){
        return new ResponseEntity<>(concertService.getAllConcertByParams(new ConcertDto(country, startDate, finishDate, place)), HttpStatus.OK);
    }

    @Operation(summary = "Delete concert", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert deleted",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concert not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
            @ApiResponse(responseCode = "403", description = "Resource forbidden")
    })
    @DeleteMapping(value = "/rest/api/v1/concerts/{id}")
    public ResponseEntity<Concert> deleteConcertById(@PathVariable("id") Long id){
        concertService.deleteConcert(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update concert", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert updated",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Concert not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
            @ApiResponse(responseCode = "403", description = "Resource forbidden")
    })
    @PutMapping(value = "/rest/api/v1/concerts/{id}")
    public ResponseEntity<Concert> updatePlace(@PathVariable("id") Long id, @RequestBody ConcertDto concertDto){
        concertService.updateConcert(concertDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
