package com.example.onlinemarket.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.onlinemarket.ActiveEnum;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    Product cleaner = new Product("Cleaner3", "Incredible product3", 19.99, 5, ActiveEnum.ONLINE);

    @BeforeEach
    void setUp() {
        productRepository.save(cleaner);
    }

    @AfterEach
    void tearDown() {
        productRepository.delete(cleaner);
    }
    //
    //    @Test
    //    public void givenOneProduct_WhenGetAllProducts_ThenCorrectProduct() throws Exception {
    //        mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(status().isOk())
    //                .andExpect(jsonPath("$",hasSize(1)));
    //    }
}
