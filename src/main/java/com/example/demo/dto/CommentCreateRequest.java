package com.example.demo.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CommentCreateRequest {

    private String text;

    private long userId;

    private long newsId;
}
