package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@SuperBuilder
public class UserDto {

    private Long id;


    private String username;


    //Set<News> newsList;


    //List<CommentDto> commentsDtosList;

}
