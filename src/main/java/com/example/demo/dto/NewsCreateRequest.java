package com.example.demo.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class NewsCreateRequest {


    private String title;
    private String content;


    private long userId;


    private String categoryName;
}
