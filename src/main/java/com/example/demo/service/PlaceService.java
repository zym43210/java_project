package com.example.demo.service;


import com.example.demo.dto.PlaceDto;
import com.example.demo.entity.Place;

import java.util.Collection;

public interface PlaceService {
    Collection<Place> getAllPlaces();
    Place getPlaceById(Long id);
    Place getPlaceByName(String name);
    void savePlace(PlaceDto placeDto);
    Collection<String> convertToNames();
    Collection<String> getAllCountries();
    Collection<String> getAllPlaceNameByCountryName(String name);
    void deletePlace(Long id);
    Place updatePlace(PlaceDto place, Long placeId);
}
