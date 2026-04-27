package com.ecommerce.service;

import com.ecommerce.dto.OrderDto;
import java.util.List;

public interface OrderService {
    OrderDto.Response placeOrder(String email, OrderDto.PlaceOrderRequest request);
    OrderDto.Response getOrderById(Long orderId, String email);
    List<OrderDto.Response> getUserOrders(String email);
    OrderDto.Response updateOrderStatus(Long orderId, String status);
    List<OrderDto.Response> getAllOrders();
}
