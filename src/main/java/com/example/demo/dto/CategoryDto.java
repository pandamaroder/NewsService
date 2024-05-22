package com.example.demo.dto;

import com.example.demo.model.News;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class CategoryDto {

    private Long id;

    private String name;

    //зачем - мысль что ссписок новостей можкт понадобится при апдейте категориии
    //private List<NewsDto> newsList;
}
