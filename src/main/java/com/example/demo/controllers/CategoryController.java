package com.example.demo.controllers;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        CategoryCreateResponse createdcategory = categoryService.createCategory(categoryCreateRequest);
        return new ResponseEntity<>(createdcategory, HttpStatus.CREATED);
    }

    @PutMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("id") int id) {
        CategoryDto deletedCategory = categoryService.deleteCategory(id);
        return new ResponseEntity<>(deletedCategory, HttpStatus.CREATED);
    }
}
