package com.example.onlinemarket.service;

import com.example.onlinemarket.entity.Product;

import java.util.List;

import org.springframework.http.ResponseEntity;

/**
 * This interface holds methods for getting,creating and manipulating products
 */
public interface ProductService {

    void initializeProducts();

    ResponseEntity<List<Product>> allProducts();

    ResponseEntity<Product> deleteProduct(long id);

    ResponseEntity<Product> getProductById(long id);

    ResponseEntity<Product> createProduct(Product product);

    ResponseEntity<Product> updateProduct(long id, Product product);

    ResponseEntity<List<Product>> getProductsByName(String name);

    ResponseEntity<List<Product>> getProductByWord(String word);

    ResponseEntity<Product> addProductToCart(long id, long id2);
}
