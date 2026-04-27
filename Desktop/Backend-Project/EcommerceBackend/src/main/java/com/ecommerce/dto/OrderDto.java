package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Getter @Setter
    public static class PlaceOrderRequest {
        @NotBlank
        private String shippingAddress;

        @NotBlank
        private String paymentMethod;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long userId;
        private String userEmail;
        private List<OrderItemResponse> orderItems;
        private BigDecimal totalAmount;
        private String status;
        private String shippingAddress;
        private String paymentMethod;
        private String paymentStatus;
        private LocalDateTime createdAt;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }
}
