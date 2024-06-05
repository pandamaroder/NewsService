package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class NewsDto {

    private long id;

    private String title;

    private String content;

    private long userId;

    private String categoryName;
}
