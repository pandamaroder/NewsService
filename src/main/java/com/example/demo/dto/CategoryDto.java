package com.example.demo.dto;

import com.example.demo.model.News;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;


public record CategoryDto(long id, String name) {



}
