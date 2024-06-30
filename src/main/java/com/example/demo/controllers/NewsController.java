package com.example.demo.controllers;

import com.example.demo.dto.NewsCreateRequest;
import com.example.demo.dto.NewsDto;
import com.example.demo.services.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/news")
@Tag(name = "News", description = "API для управления новостями")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Посмотреть новости", description = "Посмотреть все новости")
    public Collection<NewsDto> getNews(
        @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
        @RequestParam(defaultValue = "8") @Min(1) @Max(200) int elements,
        @RequestParam(required = false) boolean showConfirmed) {
        return newsService.retrieveNews(pages, elements, showConfirmed);
    }

    /*Если возвращается одна новость (findById), она должна содержать все
    комментарии к ней.*/
    @GetMapping("/by-name")
    @Operation(summary = "Посмотреть новость", description = "Просмотр новой новости")
    public Collection<NewsDto> findNews(@RequestParam(name = "title", defaultValue = "") String name) {

        return newsService.findByTitle(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новость", description = "Создание новой новости")
    public NewsDto create(@RequestBody NewsCreateRequest newsCreateRequest) {

        return newsService.createNews(newsCreateRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable("id") long id) {
        newsService.deleteNews(id);
    }

    @PutMapping
    @Operation(summary = "Обновление")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto updateNews(@RequestBody NewsDto dto) {
        return newsService.updateNews(dto);
    }
}
