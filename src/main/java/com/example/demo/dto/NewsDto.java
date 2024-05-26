package com.example.demo.dto;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

public class NewsDto {

    private long id;

    private String title;
    private String content;


    private long user_id;


    private String categoryName;
}
