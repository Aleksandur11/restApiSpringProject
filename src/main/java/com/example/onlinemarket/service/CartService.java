package com.example.onlinemarket.service;

import com.example.onlinemarket.entity.Cart;
import org.springframework.http.ResponseEntity;

/**
 * This interface holds methods for manipulating and getting Carts
 */
public interface CartService {

    ResponseEntity<Cart> getCart(long id);

    ResponseEntity<Cart> deleteProduct(long id, long id2);
}
