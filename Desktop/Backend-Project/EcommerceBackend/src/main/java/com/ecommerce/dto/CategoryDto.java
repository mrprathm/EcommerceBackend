package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class CategoryDto {

    @Getter @Setter
    public static class Request {
        @NotBlank @Size(max = 100)
        private String name;

        @Size(max = 500)
        private String description;

        private String imageUrl;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private String imageUrl;
        private int productCount;
    }
}
