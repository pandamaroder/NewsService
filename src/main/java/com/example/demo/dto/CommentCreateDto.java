package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommentCreateDto {

    @NotBlank
    private String text;

    @NotBlank
    private long userId;

    @NotBlank
    private long newsId;
}
