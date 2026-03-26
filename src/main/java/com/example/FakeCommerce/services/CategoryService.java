package com.example.FakeCommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.dtos.CategoryResponseDto;
import com.example.FakeCommerce.dtos.CreateCategoryRequestDto;
import com.example.FakeCommerce.repositories.CategoryRepository;
import com.example.FakeCommerce.schema.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private static CategoryResponseDto toResponseDto(Category c) {
        return CategoryResponseDto.builder().id(c.getId()).name(c.getName()).build();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<CategoryResponseDto> getAllCategoryResponses() {
        return categoryRepository.findAll().stream().map(CategoryService::toResponseDto).toList();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    public CategoryResponseDto getCategoryResponseById(Long id) {
        return toResponseDto(getCategoryById(id));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponseDto createCategory(CreateCategoryRequestDto requestDto) {
        Category category = Category.builder().name(requestDto.getName()).build();
        Category saved = categoryRepository.save(category);
        return toResponseDto(saved);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

