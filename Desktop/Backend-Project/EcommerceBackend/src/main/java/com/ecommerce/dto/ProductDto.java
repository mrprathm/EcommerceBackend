package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank @Size(max = 200)
        private String name;

        private String description;

        @NotNull @DecimalMin("0.01")
        private BigDecimal price;

        @NotNull @Min(0)
        private Integer stockQuantity;

        private String imageUrl;

        @NotNull
        private Long categoryId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stockQuantity;
        private String imageUrl;
        private boolean active;
        private Long categoryId;
        private String categoryName;
        private LocalDateTime createdAt;
    }

    @Getter @Setter
    public static class PageResponse {
        private java.util.List<Response> content;
        private int pageNo;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean last;
    }
}
