package com.example.demo.dto;

import com.example.demo.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @NotNull(message = "Enter the rating")
    private Integer rating;
    @NotBlank(message = "Enter text")
    @Size(min = 10, max = 150, message = "Text size between 10 and 150 symbols")
    private String text;
    private Long concertId;
    private Collection<File> filename;
}
