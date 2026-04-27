package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    @Autowired private OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order from cart")
    public ResponseEntity<ApiResponse<OrderDto.Response>> placeOrder(
            Authentication auth, @Valid @RequestBody OrderDto.PlaceOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully",
                        orderService.placeOrder(auth.getName(), request)));
    }

    @GetMapping("/my-orders")
    @Operation(summary = "Get current user's orders")
    public ResponseEntity<ApiResponse<List<OrderDto.Response>>> getMyOrders(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Orders fetched",
                orderService.getUserOrders(auth.getName())));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<ApiResponse<OrderDto.Response>> getOrderById(
            @PathVariable Long orderId, Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Order found",
                orderService.getOrderById(orderId, auth.getName())));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders (Admin only)")
    public ResponseEntity<ApiResponse<List<OrderDto.Response>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success("All orders fetched", orderService.getAllOrders()));
    }

    @PutMapping("/admin/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status (Admin only)")
    public ResponseEntity<ApiResponse<OrderDto.Response>> updateStatus(
            @PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Order status updated",
                orderService.updateOrderStatus(orderId, status)));
    }
}
