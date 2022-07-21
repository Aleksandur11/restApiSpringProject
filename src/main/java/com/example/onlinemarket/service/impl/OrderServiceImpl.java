package com.example.onlinemarket.service.impl;

import com.example.onlinemarket.ActiveEnum;
import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.Order;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.entity.User;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.OrderRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.repository.UserRepository;
import com.example.onlinemarket.service.OrderService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class hold methods for adding products to the order and getting the order
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * This method takes all the products from a cart and puts them in the order
     *
     * @param id the id of the cart from where we will take the products
     * @return ResponseEntity with null body and status: not found if there isn't a cart with this id
     * or ResponseEntity with the order and status:Created
     */
    @Override
    public ResponseEntity<Order> addProductsFromCartToOrders(long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Order order = new Order();
        order.setUser(user.get());
        List<Product> products = cart.get().getProducts();
        for (Product product : products) {
            if (!product.getActive().equals(ActiveEnum.OFFLINE)) {
                product.setQuantity(product.getQuantity() - 1);
            }
            if (product.getQuantity() == 0) {
                product.setActive(ActiveEnum.OFFLINE);
            }
            productRepository.save(product);
        }
        products.removeIf(p -> p.getActive().equals(ActiveEnum.OFFLINE));
        for (Product p : products) {
            order.setTotalAmount(order.getTotalAmount() + p.getPrice());
        }
        order.setProductList(products);
        cart.get().setProducts(List.of());
        orderRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @param id id of the order
     * @return ResponseEntity with null and status:Not found if there isn't an order with the given id or
     * returns ResponseEntity with the given order and status:OK
     */
    @Override
    public ResponseEntity<Order> getOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }
}
