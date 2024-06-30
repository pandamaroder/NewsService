package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@RequiredArgsConstructor
public class CommentCreateDto {

    @NotBlank
    private String text;

    @NotBlank
    private long userId;

    @NotBlank
    private long newsId;
}
