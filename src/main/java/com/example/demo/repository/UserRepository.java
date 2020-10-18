package com.example.demo.repository;

import com.example.demo.model.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UsersEntity, Integer> {

    public UsersEntity findByLogin(String name);

    public UsersEntity findByLoginAndPassword(String name, String pass);
}
