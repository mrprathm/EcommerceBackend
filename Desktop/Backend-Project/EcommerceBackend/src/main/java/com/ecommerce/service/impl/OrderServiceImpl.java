package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartService cartService;

    @Override
    @Transactional
    public OrderDto.Response placeOrder(String email, OrderDto.PlaceOrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty"));

        if (cart.getCartItems().isEmpty())
            throw new IllegalArgumentException("Cannot place order with empty cart");

        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.getShippingAddress())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus("PENDING")
                .status(Order.OrderStatus.PENDING)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity())
                throw new IllegalArgumentException("Insufficient stock for: " + product.getName());

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(subtotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();
            orderItems.add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(email);

        return toDto(savedOrder);
    }

    @Override
    public OrderDto.Response getOrderById(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getEmail().equals(email)
                && !order.getUser().getRole().name().equals("ADMIN"))
            throw new IllegalArgumentException("Access denied");

        return toDto(order);
    }

    @Override
    public List<OrderDto.Response> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findByUserId(user.getId()).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto.Response updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        return toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto.Response> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private OrderDto.Response toDto(Order order) {
        List<OrderDto.OrderItemResponse> items = order.getOrderItems().stream().map(item ->
                OrderDto.OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build()
        ).collect(Collectors.toList());

        return OrderDto.Response.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .userEmail(order.getUser().getEmail())
                .orderItems(items)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
