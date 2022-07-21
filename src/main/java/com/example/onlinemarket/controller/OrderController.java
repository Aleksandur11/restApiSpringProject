package com.example.onlinemarket.controller;

import com.example.onlinemarket.entity.Order;
import com.example.onlinemarket.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller for orders, it holds methods that are requests for adding and getting orders
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * This request creates an order from products in cart
     * It takes the id of the cart and puts all the items of the cart in the order if the quantity is more than 0
     *
     * @param id - cart id
     * @return order in json format with products in it
     */
    @PostMapping("/add/{id}")
    public ResponseEntity<Order> createOrder(@PathVariable long id) {
        return orderService.addProductsFromCartToOrders(id);
    }

    /**
     * This method creates a request to get an order by its id
     *
     * @param id - long - order id
     * @return the order if exists else http status not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) {
        return orderService.getOrder(id);
    }
}
