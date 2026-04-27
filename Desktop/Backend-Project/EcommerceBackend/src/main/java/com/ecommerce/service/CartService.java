package com.ecommerce.service;

import com.ecommerce.dto.CartDto;

public interface CartService {
    CartDto.Response getCart(String email);
    CartDto.Response addItem(String email, CartDto.AddItemRequest request);
    CartDto.Response updateItemQuantity(String email, Long cartItemId, Integer quantity);
    CartDto.Response removeItem(String email, Long cartItemId);
    void clearCart(String email);
}
