package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.IncorrectPasswordException;
import com.example.demo.exceptions.UserNameNotFoundException;

public interface UserService {
    User login(UserDto userDto) throws UserNameNotFoundException, IncorrectPasswordException;
    void register(UserDto userDto) throws Exception;
    boolean isAdmin(String username);
    User getUserByUsername(String username);
}
