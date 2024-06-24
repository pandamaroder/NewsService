package com.example.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@RequiredArgsConstructor
public class CommentDto {

    private Long id;

    private String text;

    private Long userId;

    private Long newsId;
}
