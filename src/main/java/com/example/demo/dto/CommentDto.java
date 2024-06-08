package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@SuperBuilder
@RequiredArgsConstructor
public class CommentDto {

    private long id;

    private String text;

    private long userId;

    private long newsId;
}
