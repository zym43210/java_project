package com.example.demo.repository;

import com.example.demo.model.ConcertEntity;
import com.example.demo.model.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends CrudRepository<ConcertEntity, Integer> {


}
