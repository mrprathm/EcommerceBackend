package com.ecommerce.service.impl;

import com.ecommerce.dto.CartDto;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    private Cart getOrCreateCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart cart = Cart.builder().user(user).build();
            return cartRepository.save(cart);
        });
    }

    @Override
    public CartDto.Response getCart(String email) {
        Cart cart = getOrCreateCart(email);
        return toDto(cart);
    }

    @Override
    @Transactional
    public CartDto.Response addItem(String email, CartDto.AddItemRequest request) {
        Cart cart = getOrCreateCart(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.getCartItems().add(newItem);
        }

        return toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDto.Response updateItemQuantity(String email, Long cartItemId, Integer quantity) {
        Cart cart = getOrCreateCart(email);
        CartItem item = cart.getCartItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity <= 0) {
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }
        return toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDto.Response removeItem(String email, Long cartItemId) {
        Cart cart = getOrCreateCart(email);
        cart.getCartItems().removeIf(i -> i.getId().equals(cartItemId));
        return toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void clearCart(String email) {
        Cart cart = getOrCreateCart(email);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private CartDto.Response toDto(Cart cart) {
        List<CartDto.CartItemResponse> items = cart.getCartItems().stream().map(item -> {
            BigDecimal subtotal = item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            return CartDto.CartItemResponse.builder()
                    .cartItemId(item.getId())
                    .productId(item.getProduct().getId())
                    .productName(item.getProduct().getName())
                    .productImageUrl(item.getProduct().getImageUrl())
                    .unitPrice(item.getProduct().getPrice())
                    .quantity(item.getQuantity())
                    .subtotal(subtotal)
                    .build();
        }).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(CartDto.CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartDto.Response.builder()
                .cartId(cart.getId())
                .items(items)
                .totalAmount(total)
                .totalItems(items.size())
                .build();
    }
}
