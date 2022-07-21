package com.example.onlinemarket.controller;

import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.service.ProductService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller for products, it holds methods that are requests for CRUD operations
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * This method returns all products that are in the database
     *
     * @return All products that are in the database
     */
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.allProducts();
    }

    /**
     * This method returns product by his id if exist in the database if not it returns http status not found
     *
     * @param code - the id of the product
     * @return product with the given id if exists if not returns https status not found
     */
    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductById(@PathVariable long code) {
        return productService.getProductById(code);
    }

    /**
     * This method creates a product in the database
     *
     * @param product product object from the body of the request
     * @return Product - created product
     */
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * This method deletes a product by the id from the database
     *
     * @param code id of the product
     * @return http status not found if deleted
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long code) {
        return productService.deleteProduct(code);
    }

    /**
     * This method get the product from the database by his code and update the information with the information from the body
     *
     * @param code    id of the product
     * @param product Product from the body of the request
     * @return the updated product
     */
    @PutMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable long code, @RequestBody Product product) {
        return productService.updateProduct(code, product);
    }

    /**
     * This method returns product by his name if it exists or http status not found if not
     *
     * @param name String - name of the product we search
     * @return the product if it exists in the database or http status not found
     */
    @GetMapping("/names/{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    /**
     * This method returns product by the word in the description or http status not found
     *
     * @param word String word from the description
     * @return the product wich contains this word in the description or http status not found
     */
    @GetMapping("/word/{word}")
    public ResponseEntity<List<Product>> getProductByWord(@PathVariable String word) {
        return productService.getProductByWord(word);
    }

    /**
     * This method adds the product by his id to the cart
     *
     * @param id  the id of the product we want to add to the cart
     * @param id2 the id of the user cart we want to add the product to
     * @return the product that is added to the cart
     */
    @PostMapping("/{id}/user/{id2}")
    public ResponseEntity<Product> addProductToCart(@PathVariable long id, @PathVariable long id2) {
        return productService.addProductToCart(id, id2);
    }
}
