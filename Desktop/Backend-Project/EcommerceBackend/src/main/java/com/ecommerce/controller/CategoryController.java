package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryDto;
import com.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<CategoryDto.Response>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success("Categories fetched", categoryService.getAllCategories()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Category found", categoryService.getCategoryById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create category (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<CategoryDto.Response>> createCategory(
            @Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created", categoryService.createCategory(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update category (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<CategoryDto.Response>> updateCategory(
            @PathVariable Long id, @Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.ok(ApiResponse.success("Category updated", categoryService.updateCategory(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted", null));
    }
}
