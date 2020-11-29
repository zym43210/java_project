package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private Long id;
    @NotBlank(message = "Place name can not be null")
    @Size(min = 5, message = "Concert name must contains at least 5 symbols")
    private String name;
    @NotBlank(message = "Country can not be null")
    private String country;
    @NotBlank(message = "City can not be null")
    private String city;
    @NotBlank(message = "Street can not be null")
    private String street;
    @NotNull(message = "Number of building can not be null")
    private Integer placeNumber;
}
