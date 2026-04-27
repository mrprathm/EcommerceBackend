package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    @Autowired private ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products (paginated)")
    public ResponseEntity<ApiResponse<ProductDto.PageResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(ApiResponse.success("Products fetched",
                productService.getAllProducts(pageNo, pageSize, sortBy, sortDir)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ApiResponse<ProductDto.Response>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Product found", productService.getProductById(id)));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<ApiResponse<ProductDto.PageResponse>> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(ApiResponse.success("Products fetched",
                productService.getProductsByCategory(categoryId, pageNo, pageSize)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by keyword")
    public ResponseEntity<ApiResponse<ProductDto.PageResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(ApiResponse.success("Search results",
                productService.searchProducts(keyword, pageNo, pageSize)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<ProductDto.Response>> createProduct(
            @Valid @RequestBody ProductDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created", productService.createProduct(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<ProductDto.Response>> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductDto.Request request) {
        return ResponseEntity.ok(ApiResponse.success("Product updated", productService.updateProduct(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product (Admin only)", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted", null));
    }
}
