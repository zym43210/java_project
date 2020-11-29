package com.example.demo.rest;

import com.example.demo.dto.UserDto;
import com.example.demo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(value = "*")
@RestController
public class RegisterRestController {
    private final UserServiceImpl userService;


    @Autowired
    public RegisterRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Wrong format")
    })
    @PostMapping(value = "/rest/api/v1/register")
    public ResponseEntity register(@RequestBody @Valid UserDto userDto){
        try {
            userService.register(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
