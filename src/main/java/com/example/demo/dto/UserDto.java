package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
//почему создается без конструктора
public class UserDto {

    private long id;

    private String username;


    //Set<NewsDto> newsList;


    //List<CommentDto> commentsDtosList;

}
