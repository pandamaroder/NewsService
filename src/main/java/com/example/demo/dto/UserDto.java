package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class UserDto {

    private Long id;

    private String username;


    //Set<NewsDto> newsList;


    //List<CommentDto> commentsDtosList;

}
