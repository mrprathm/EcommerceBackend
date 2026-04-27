package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto.Response createProduct(ProductDto.Request request);
    ProductDto.Response getProductById(Long id);
    ProductDto.PageResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto.PageResponse getProductsByCategory(Long categoryId, int pageNo, int pageSize);
    ProductDto.PageResponse searchProducts(String keyword, int pageNo, int pageSize);
    ProductDto.Response updateProduct(Long id, ProductDto.Request request);
    void deleteProduct(Long id);
}
