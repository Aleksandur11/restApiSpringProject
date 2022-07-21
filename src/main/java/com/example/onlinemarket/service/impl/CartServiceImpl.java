package com.example.onlinemarket.service.impl;

import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.service.CartService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class holds methods for creating and manipulating cart
 */
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * This method returns a cart if there is one for the given id
     *
     * @param id long id of the cart
     * @return status ResponseEntity with null body and status:Not found if there isn't a cart with the given id
     * returns ResponseEntity with cart in the body and status:OK
     */
    @Override
    public ResponseEntity<Cart> getCart(long id) {
        Optional<Cart> byId = cartRepository.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Cart cart = byId.get();
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * This method deletes a product from the cart if the product and the cart exist
     *
     * @param id  the id of the cart
     * @param id2 the id of the product
     * @return ResponseEntity with null body and status:Not Found,
     * if there isn't cart with the given id or if there isn't a product in the cart with given id or
     * returns ResponseEntity with the cart without the deleted product and status OK
     */
    @Override
    public ResponseEntity<Cart> deleteProduct(long id, long id2) {
        Optional<Cart> byId = cartRepository.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Cart cart = byId.get();
        Optional<Product> byId1 = productRepository.findById(id2);
        if (byId1.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<Product> products = cart.getProducts();
        Product product = byId1.get();
        List<Product> contained = deleteProduct(products, product);
        cart.setProducts(contained);
        cartRepository.save(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * This method removes given product from given list if the product is in the list
     *
     * @param products list of products
     * @param product2 product to be deleted
     * @return the list without the deleted product if the product for removing was in the list or returns the initial list
     */
    private List<Product> deleteProduct(List<Product> products, Product product2) {
        for (Product p : products) {
            if (p.getCode() == (product2.getCode())) {
                products.remove(p);
                return products;
            }
        }
        return products;
    }
}
