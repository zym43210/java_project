package com.example.demo.service;

import com.example.demo.model.UsersEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Optional<UsersEntity> findByLogin(String name) {
        return Optional.of(repository.findByLogin(name));
    }

    public UsersEntity findByNameAndPassword(String name, String pass) {
        return repository.findByLoginAndPassword(name, pass);
    }

    public List getAll() {
        return (List) repository.findAll();
    }

    public void save(UsersEntity userModel) {
        repository.save(userModel);
    }
}
