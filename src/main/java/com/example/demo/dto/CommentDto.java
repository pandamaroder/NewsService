package com.example.demo.dto;

import com.example.demo.model.News;
import com.example.demo.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CommentDto {

    private long id;

    private String text;


    private long userId;


    private long newsId;
}
