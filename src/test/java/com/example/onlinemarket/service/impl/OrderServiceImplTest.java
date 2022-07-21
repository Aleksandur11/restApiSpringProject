package com.example.onlinemarket.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.onlinemarket.ActiveEnum;
import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.Order;
import com.example.onlinemarket.entity.Product;
import com.example.onlinemarket.entity.User;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.OrderRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.repository.UserRepository;
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
class OrderServiceImplTest {
    @Mock
    private OrderRepository mockedOrderRepository;

    @Mock
    private ProductRepository mockedProductRepository;

    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private CartRepository mockedCartRepository;

    private OrderServiceImpl serviceToTest;

    private final Product cleaner = new Product("Cleaner", "Incredible product", 9.99, 5, ActiveEnum.ONLINE);
    private final Product cleaner2 = new Product("Cleaner2", "Incredible product", 9.99, 0, ActiveEnum.OFFLINE);
    private final User gosho =
            new User("gopeto", "12345", "Goshko", "goshkobmw@gmail.com", "0896392101", "Liulin 5", "At the door");

    @BeforeEach
    public void init() {
        serviceToTest = new OrderServiceImpl(
                mockedOrderRepository, mockedProductRepository, mockedUserRepository, mockedCartRepository);
    }

    @Test
    public void givenWrongCartId_WhenAddingProductsFromCartToOrder_ThenNull() {
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Order> orderResponseEntity = serviceToTest.addProductsFromCartToOrders(2L);
        HttpStatus statusCode = orderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Order body = orderResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingCartId_WhenAddingProductsFromCartToOrder_ThenNewOrder() {
        Cart cart = new Cart();
        List<Product> productList = new ArrayList<>();
        productList.add(cleaner);
        productList.add(cleaner2);
        cart.setProducts(productList);
        Mockito.when(mockedCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        Mockito.when(mockedUserRepository.findById(1L)).thenReturn(Optional.of(gosho));
        ResponseEntity<Order> orderResponseEntity = serviceToTest.addProductsFromCartToOrders(1L);
        HttpStatus statusCode = orderResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        List<Product> productList1 = orderResponseEntity.getBody().getProductList();
        double totalAmount = orderResponseEntity.getBody().getTotalAmount();
        double expectedAmout = 9.99;
        assertEquals(statusCode, expectedStatusCode);
        assertEquals(productList1, productList);
        assertEquals(expectedAmout, totalAmount);
    }

    @Test
    public void givenNonExistingUserId_WhenGetOrder_ThenNull() {
        Mockito.when(mockedOrderRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Order> order1 = serviceToTest.getOrder(2L);
        HttpStatus statusCode = order1.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Order body = order1.getBody();
        assertNull(body);
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void givenExistingUserId_WhenGetOrder_ThenGetTheUserOrder() {
        List<Product> productList = new ArrayList<>();
        Order order = new Order();
        productList.add(cleaner);
        productList.add(cleaner2);
        order.setProductList(productList);
        order.setUser(gosho);
        order.setTotalAmount(cleaner.getPrice());
        Mockito.when(mockedOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        ResponseEntity<Order> order1 = serviceToTest.getOrder(1L);
        HttpStatus statusCode = order1.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        assertEquals(expectedStatusCode, statusCode);
        Order body = order1.getBody();
        assertEquals(body, order);
    }
}
