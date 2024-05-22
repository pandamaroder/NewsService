package com.example.demo.services;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryCreateResponse createCategory(CategoryCreateRequest request) {
        Category category = Category.builder().name(request.name()).build();
        Category savedCategory = categoryRepository.save(category);
        return new CategoryCreateResponse(savedCategory.getId(), savedCategory.getName());
    }

    @Transactional
    public CategoryDto deleteCategory(long userId) {
        Category category = categoryRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Категории с таким ID не существует"));;
        categoryRepository.delete(category);
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId()).build();
    }

    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        //нужен ли здесь categoryId?
        Category category = Category.builder().name(categoryDto.getName()).build();
        categoryRepository.save(category);
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId()).build();
    }
}
