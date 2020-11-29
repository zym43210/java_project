package com.example.demo.repository;

import com.example.demo.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findPlaceByName(String name);

    @Query(value = "SELECT distinct place_country from PLACES p join CONCERTS e on e.place_id = p.place_id", nativeQuery = true)
    Collection<String> getAllCountries();

    @Query(value = "SELECT distinct place_name from PLACES p join CONCERTS e on e.place_id = p.place_id where place_country = ?1", nativeQuery = true)
    Optional<Collection<String>> getAllPlaceNamesByCountry(String name);

    void deletePlaceById(Long id);
}
