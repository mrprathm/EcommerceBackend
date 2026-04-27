package com.ecommerce.service;

import com.ecommerce.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto.Response createCategory(CategoryDto.Request request);
    CategoryDto.Response getCategoryById(Long id);
    List<CategoryDto.Response> getAllCategories();
    CategoryDto.Response updateCategory(Long id, CategoryDto.Request request);
    void deleteCategory(Long id);
}
