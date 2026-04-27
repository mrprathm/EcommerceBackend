package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Override
    public ProductDto.Response createProduct(ProductDto.Request request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .imageUrl(request.getImageUrl())
                .category(category)
                .active(true)
                .build();

        return toDto(productRepository.save(product));
    }

    @Override
    public ProductDto.Response getProductById(Long id) {
        return toDto(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public ProductDto.PageResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<Product> page = productRepository.findByActiveTrue(PageRequest.of(pageNo, pageSize, sort));
        return toPageResponse(page);
    }

    @Override
    public ProductDto.PageResponse getProductsByCategory(Long categoryId, int pageNo, int pageSize) {
        Page<Product> page = productRepository.findByCategoryIdAndActiveTrue(
                categoryId, PageRequest.of(pageNo, pageSize));
        return toPageResponse(page);
    }

    @Override
    public ProductDto.PageResponse searchProducts(String keyword, int pageNo, int pageSize) {
        Page<Product> page = productRepository.searchProducts(keyword, PageRequest.of(pageNo, pageSize));
        return toPageResponse(page);
    }

    @Override
    public ProductDto.Response updateProduct(Long id, ProductDto.Request request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    private ProductDto.Response toDto(Product p) {
        return ProductDto.Response.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .imageUrl(p.getImageUrl())
                .active(p.isActive())
                .categoryId(p.getCategory().getId())
                .categoryName(p.getCategory().getName())
                .createdAt(p.getCreatedAt())
                .build();
    }

    private ProductDto.PageResponse toPageResponse(Page<Product> page) {
        ProductDto.PageResponse r = new ProductDto.PageResponse();
        r.setContent(page.getContent().stream().map(this::toDto).collect(Collectors.toList()));
        r.setPageNo(page.getNumber());
        r.setPageSize(page.getSize());
        r.setTotalElements(page.getTotalElements());
        r.setTotalPages(page.getTotalPages());
        r.setLast(page.isLast());
        return r;
    }
}
