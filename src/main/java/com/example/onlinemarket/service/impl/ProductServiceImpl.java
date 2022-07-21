package com.example.onlinemarket.service.impl;

import com.example.onlinemarket.ActiveEnum;
import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.service.ProductService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class holds methods for initializing products in the database and manipulate them
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * This method puts 2 products in the database
     */
    @Override
    public void initializeProducts() {
        if (productRepository.count() == 0) {
            Product cleaner = new Product("Cleaner", "Incredible product", 9.99, 5, ActiveEnum.ONLINE);
            Product washer = new Product("Washer", "Super product", 19.99, 5, ActiveEnum.ONLINE);
            productRepository.saveAll(List.of(cleaner, washer));
        }
    }

    /**
     * This method returns a list with all products in the database
     *
     * @return ResponseEntity with list containing all products in the database and status:OK
     */
    @Override
    public ResponseEntity<List<Product>> allProducts() {
        List<Product> all = productRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    /**
     * This method deletes a product from the database if the product with the given id exists in the database
     *
     * @param id long the parameter of the product
     * @return ResponseEntity with null and status:Not found if there isn't product with the given id
     * or returns ResponseEntity with null in the body and status:No content
     */
    @Override
    public ResponseEntity<Product> deleteProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        productRepository.delete(product.get());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * This method gets a Product by the given id if the database contains this product
     *
     * @param id long the id of the product
     * @return ResponseEntity with null body and status:Not found if there isn't a product with the given id or
     * returns ResponseEntity with the product and status:OK
     */
    @Override
    public ResponseEntity<Product> getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    /**
     * This method creates a given product in the database
     *
     * @param product Product object to be created in the database
     * @return ResponseEntity with the created product and status:Created
     */
    @Override
    public ResponseEntity<Product> createProduct(Product product) {
        if (product.getQuantity() == 0) {
            product.setActive(ActiveEnum.OFFLINE);
        }
        Product createdProduct = productRepository.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * This method updates given product with given Product who contains new info
     *
     * @param id      the id of the product we want to update
     * @param product the Product object with the new info to be updated
     * @return ResponseEntity with null body and status:Not found if there isn't a product with the given id or
     * returns ResponseEntity with the updated product and status:Ok
     */
    @Override
    public ResponseEntity<Product> updateProduct(long id, Product product) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (oldProduct.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Product newProduct = productRepository.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    /**
     * This method returns product/s with the given name if they exist in the database
     *
     * @param name String name of the product
     * @return ResponseEntity with null body and status:Not found if there isn't a product with the given name in the database or
     * return ResponseEntity with the products and status:OK
     */
    @Override
    public ResponseEntity<List<Product>> getProductsByName(String name) {
        Optional<List<Product>> product = productRepository.findAllByName(name);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    /**
     * @param word String word that description must contains
     * @return ResponseEntity with null body and status:Not found if there isn't a product with the given word in the description in the database or
     * return ResponseEntity with the products and status:OK
     */
    @Override
    public ResponseEntity<List<Product>> getProductByWord(String word) {
        Optional<List<Product>> product = productRepository.findAllByDescriptionContaining(word);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    /**
     * @param id  - long - the id of the product we want to add to the cart
     * @param id2 - long - the id of the cart we want to add the product to
     * @return ResponseEntity with null body and status:Not found if there isn't product or cart with the given ids or
     * returns ResponseEntity with the added product and status: OK
     */
    @Override
    public ResponseEntity<Product> addProductToCart(long id, long id2) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Optional<Cart> currentUserCart = cartRepository.findById(id2);
        if (currentUserCart.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Cart cart = currentUserCart.get();
        List<Product> products = cart.getProducts();
        products.add(product.get());
        cart.setProducts(products);
        cartRepository.save(cart);
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }
}
