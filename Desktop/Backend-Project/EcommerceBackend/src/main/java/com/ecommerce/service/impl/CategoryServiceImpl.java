package com.ecommerce.service.impl;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired private CategoryRepository categoryRepository;

    @Override
    public CategoryDto.Response createCategory(CategoryDto.Request request) {
        if (categoryRepository.existsByName(request.getName()))
            throw new IllegalArgumentException("Category already exists: " + request.getName());

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();

        return toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto.Response getCategoryById(Long id) {
        return toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id)));
    }

    @Override
    public List<CategoryDto.Response> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto.Response updateCategory(Long id, CategoryDto.Request request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        return toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id))
            throw new ResourceNotFoundException("Category not found with id: " + id);
        categoryRepository.deleteById(id);
    }

    private CategoryDto.Response toDto(Category c) {
        return CategoryDto.Response.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .imageUrl(c.getImageUrl())
                .productCount(c.getProducts() != null ? c.getProducts().size() : 0)
                .build();
    }
}
