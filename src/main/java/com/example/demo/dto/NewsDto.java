package com.example.demo.dto;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
