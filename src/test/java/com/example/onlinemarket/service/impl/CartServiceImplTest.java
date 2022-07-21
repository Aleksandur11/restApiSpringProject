package com.example.onlinemarket.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.onlinemarket.ActiveEnum;
import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    private Cart cart = new Cart();

    @Mock
    private CartRepository mockedCartRepository;

    @Mock
    private ProductRepository mockedProductRepository;

    private CartServiceImpl serviceToTest;
    private Product product1 = new Product("Cleaner", "Incredible", 10.99, 5, ActiveEnum.ONLINE);
    private Product product2 = new Product("Cleaner2", "Incredible product", 11.99, 5, ActiveEnum.ONLINE);

    @BeforeEach
    void init() {
        serviceToTest = new CartServiceImpl(mockedCartRepository, mockedProductRepository);
    }

    @Test
    public void givenCartId_WhenGetCart_ThenCorrectCart() {
        List<Product> products = List.of(product1, product2);
        cart.setProducts(products);

        Mockito.when(mockedCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        ResponseEntity<Cart> cart1 = serviceToTest.getCart(1L);
        HttpStatus statusCode = cart1.getStatusCode();
        Cart body = cart1.getBody();
        HttpStatus expected = HttpStatus.OK;
        assertEquals(expected, statusCode);
        assertEquals(body, cart);
    }

    @Test
    public void givenNotExistingCartId_WhenGetCart_ThenNull() {
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Cart> cart1 = serviceToTest.getCart(2L);
        HttpStatus statusCode = cart1.getStatusCode();
        Cart body = cart1.getBody();
        HttpStatus expected = HttpStatus.NOT_FOUND;
        assertEquals(expected, statusCode);
        assertNull(body);
    }
    // TODO:
    @Test
    public void givenCartWith2Products_WhenDeleteProduct_ThenOneProduct() {
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        cart.setProducts(products);
        Mockito.when(mockedCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockedProductRepository.findById(1L)).thenReturn(Optional.of(product1));
        ResponseEntity<Cart> cartResponseEntity = serviceToTest.deleteProduct(1L, 1L);
        HttpStatus statusCode = cartResponseEntity.getStatusCode();
        HttpStatus expectedStatus = HttpStatus.OK;
        assertEquals(expectedStatus, statusCode);
        assertEquals(1, cart.getProducts().size());
        assertEquals(product2, cart.getProducts().get(0));
    }

    @Test
    public void givenNoExistingCartId_WhenDeleteProduct_ThenNull() {
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Cart> cartResponseEntity = serviceToTest.deleteProduct(2L, 1L);
        HttpStatus statusCode = cartResponseEntity.getStatusCode();
        Cart body = cartResponseEntity.getBody();
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        assertEquals(expectedStatus, statusCode);
        assertNull(body);
    }

    @Test
    public void givenNoProductId_WhenDeleteProduct_ThenNull() {
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.of(cart));
        Mockito.when(mockedProductRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Cart> cartResponseEntity = serviceToTest.deleteProduct(2L, 1L);
        HttpStatus statusCode = cartResponseEntity.getStatusCode();
        Cart body = cartResponseEntity.getBody();
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        assertEquals(expectedStatus, statusCode);
        assertNull(body);
    }
}
