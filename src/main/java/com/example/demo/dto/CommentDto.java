package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommentDto {

    private long id;

    private String text;


    private long userId;


    private long newsId;
}
