package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CartDto;
import com.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Shopping cart APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {

    @Autowired private CartService cartService;

    @GetMapping
    @Operation(summary = "Get current user's cart")
    public ResponseEntity<ApiResponse<CartDto.Response>> getCart(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success("Cart fetched", cartService.getCart(auth.getName())));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ApiResponse<CartDto.Response>> addItem(
            Authentication auth, @Valid @RequestBody CartDto.AddItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item added", cartService.addItem(auth.getName(), request)));
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<ApiResponse<CartDto.Response>> updateItem(
            Authentication auth,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(ApiResponse.success("Cart updated",
                cartService.updateItemQuantity(auth.getName(), cartItemId, quantity)));
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ApiResponse<CartDto.Response>> removeItem(
            Authentication auth, @PathVariable Long cartItemId) {
        return ResponseEntity.ok(ApiResponse.success("Item removed",
                cartService.removeItem(auth.getName(), cartItemId)));
    }

    @DeleteMapping
    @Operation(summary = "Clear entire cart")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication auth) {
        cartService.clearCart(auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }
}
