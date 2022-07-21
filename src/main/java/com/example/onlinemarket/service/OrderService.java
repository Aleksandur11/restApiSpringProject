package com.example.onlinemarket.service;

import com.example.onlinemarket.entity.Order;
import org.springframework.http.ResponseEntity;

/**
 * This interface holds the methods for manipulating and getting Orders
 */
public interface OrderService {

    ResponseEntity<Order> addProductsFromCartToOrders(long id);

    ResponseEntity<Order> getOrder(long id);
}
