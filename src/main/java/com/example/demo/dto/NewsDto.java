package com.example.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;
import java.util.Objects;

@Data
@Validated
@SuperBuilder
@RequiredArgsConstructor
public class NewsDto {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsDto newsDto)) {
            return false;
        }
        return Objects.equals(title, newsDto.title.trim().toLowerCase(Locale.ROOT)) &&
            Objects.equals(content, newsDto.content) &&
            Objects.equals(categoryName, newsDto.categoryName) &&
            userId.equals(newsDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, categoryName, title);
    }
}
