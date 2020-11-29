package com.example.demo.rest;

import com.example.demo.dto.PlaceDto;
import com.example.demo.entity.Place;
import com.example.demo.service.impl.ConcertServiceImpl;
import com.example.demo.service.impl.PlaceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(value = "*")
@RestController
public class PlaceRestController {
    private final PlaceServiceImpl placeService;
    private final ConcertServiceImpl concertService;

    @Autowired
    public PlaceRestController(PlaceServiceImpl placeService, ConcertServiceImpl concertService) {
        this.placeService = placeService;
        this.concertService = concertService;
    }
    @Operation(summary = "Get all place names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return places",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/rest/api/v1/places/names")
    public ResponseEntity<Collection<String>> getAllPlaceNames(){
        return new ResponseEntity<>(placeService.convertToNames(), HttpStatus.OK);
    }

    @Operation(summary = "Add place", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place added",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @PostMapping(value = "/rest/api/v1/places")
    public ResponseEntity<PlaceDto> savePlace(@RequestBody @Valid PlaceDto placeDto){
        placeService.savePlace(placeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all place countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return countries",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/rest/api/v1/places/countries")
    public ResponseEntity<Collection<String>> getAllCountries(){
        return new ResponseEntity<>(placeService.getAllCountries(), HttpStatus.OK);
    }

    @Operation(summary = "Get all place names by country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return countries",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @GetMapping(value = "/rest/api/v1/places/placeNames")
    public ResponseEntity<Collection<String>> getAllPlaceNamesByCountry(@RequestParam("countryName") String countryName){
        return new ResponseEntity<>(placeService.getAllPlaceNameByCountryName(countryName), HttpStatus.OK);
    }

    @Operation(summary = "Get all places")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return places",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/rest/api/v1/places")
    public ResponseEntity<Collection<Place>> getAllPlaces(){
        return new ResponseEntity<>(placeService.getAllPlaces(), HttpStatus.OK);
    }

    @Operation(summary = "Delete place", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place deleted",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Place not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
            @ApiResponse(responseCode = "403", description = "Resource forbidden")
    })
    @DeleteMapping(value = "/rest/api/v1/places/{id}")
    public ResponseEntity<Place> deletePlace(@PathVariable("id") Long id){
        placeService.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get place by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place returned",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Place not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
    })
    @GetMapping(value = "/rest/api/v1/places/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable("id") Long id){
        return new ResponseEntity<>(placeService.getPlaceById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update place", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place updated",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Place not founded"),
            @ApiResponse(responseCode = "400", description = "Wrong format"),
            @ApiResponse(responseCode = "403", description = "Resource forbidden")
    })
    @PutMapping(value = "/rest/api/v1/places/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable("id") Long id, @RequestBody PlaceDto place){
        return new ResponseEntity<>(placeService.updatePlace(place, id), HttpStatus.OK);
    }
}
