package com.example.onlinemarket.init;

import com.example.onlinemarket.service.ProductService;
import com.example.onlinemarket.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class invoke methods that create products and user in the database
 */
@Component
public class DbInit implements CommandLineRunner {

    private UserService userService;
    private ProductService productService;

    public DbInit(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        productService.initializeProducts();
        userService.initializeUsers();
    }
}
