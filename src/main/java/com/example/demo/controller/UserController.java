package com.example.demo.controller;

import com.example.demo.model.UsersEntity;
import com.example.demo.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/users")
    public ResponseEntity getUsers() {
        logger.info("do filter...");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/login/{login}")
    public ResponseEntity getUserByLogin(@PathVariable(name = "login") String login) {
        Optional<UsersEntity> user = userService.findByLogin(login);
        return user.isPresent() ? ResponseEntity.ok(user.get()) :
                ResponseEntity.badRequest().body("invalid User login");
    }

    @PostMapping("/users")

    public void saveUser(@RequestBody UsersEntity userModel) {
        userService.save(userModel);
    }


}
