package com.example.onlinemarket.controller;

import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller for the Cart Entity, it has methods that are requests for deleting and getting a cart
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * This request returns a cart with http status Ok for given id if exists or http status not found if not
     *
     * @param id - the id of the cart
     * @return ResponseEntity with cart or with null
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable long id) {
        return cartService.getCart(id);
    }

    /**
     * @param id  - the id of the cart
     * @param id2 - the id of the product
     * @return ResponseEntity of cart with left products and http status OK or http status not found if cart or product is not found
     */
    @DeleteMapping("/{id}/product/{id2}")
    public ResponseEntity<Cart> deleteProducts(@PathVariable long id, @PathVariable long id2) {
        return cartService.deleteProduct(id, id2);
    }
}
