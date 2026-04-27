package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

public class CartDto {

    @Getter @Setter
    public static class AddItemRequest {
        @NotNull
        private Long productId;

        @NotNull @Min(1)
        private Integer quantity;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long cartId;
        private List<CartItemResponse> items;
        private BigDecimal totalAmount;
        private int totalItems;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CartItemResponse {
        private Long cartItemId;
        private Long productId;
        private String productName;
        private String productImageUrl;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}
