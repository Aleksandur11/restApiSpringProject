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
class ProductServiceImplTest {

    @Mock
    private ProductRepository mockedProductRepository;

    @Mock
    private CartRepository mockedCartRepository;

    private final Product cleaner = new Product("Cleaner", "Incredible product", 9.99, 5, ActiveEnum.ONLINE);
    private final Product cleaner2 = new Product("Cleaner2", "Incredible product", 9.99, 0, ActiveEnum.ONLINE);
    private ProductServiceImpl serviceToTest;

    @BeforeEach
    void init() {
        serviceToTest = new ProductServiceImpl(mockedProductRepository, mockedCartRepository);
    }

    @Test
    public void given1Product_WhenAllProducts_Then1Product() {
        Mockito.when(mockedProductRepository.findAll()).thenReturn(List.of(cleaner));
        ResponseEntity<List<Product>> listResponseEntity = serviceToTest.allProducts();
        HttpStatus statusCode = listResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        List<Product> body = listResponseEntity.getBody();
        assertEquals(1, body.size());
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(cleaner, body.get(0));
    }

    @Test
    public void givenWrongProductId_WhenGetProductById_ThenNull() {
        Mockito.when(mockedProductRepository.findById(3L)).thenReturn(Optional.empty());
        ResponseEntity<Product> productById = serviceToTest.getProductById(3L);
        HttpStatus statusCode = productById.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Product body = productById.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingProductId_WhenGetProductById_ThenCorrectProduct() {
        Mockito.when(mockedProductRepository.findById(1L)).thenReturn(Optional.of(cleaner));
        ResponseEntity<Product> productById = serviceToTest.getProductById(1L);
        HttpStatus statusCode = productById.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Product body = productById.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(cleaner, body);
    }

    @Test
    public void givenNonExistingIdOfProduct_WhenDeleteProduct_ThenNull() {
        Mockito.when(mockedProductRepository.findById(3L)).thenReturn(Optional.empty());
        ResponseEntity<Product> productResponseEntity = serviceToTest.deleteProduct(3L);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingIdOfProduct_WhenDeleteProduct_ThenProductDeleted() {
        Mockito.when(mockedProductRepository.findById(1L)).thenReturn(Optional.of(cleaner));
        ResponseEntity<Product> productResponseEntity = serviceToTest.deleteProduct(1L);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NO_CONTENT;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenProductWithMoreThan0Quantity_WhenCreateProduct_ThenProductCreated() {
        Mockito.when(mockedProductRepository.save(cleaner)).thenReturn(cleaner);
        ResponseEntity<Product> product = serviceToTest.createProduct(cleaner);
        HttpStatus statusCode = product.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        Product body = product.getBody();
        assertEquals(cleaner, body);
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void givenProductWith0Quantity_WhenCreateProduct_ThenProductWithOfflineStatusCreated() {
        Mockito.when(mockedProductRepository.save(cleaner2)).thenReturn(cleaner2);
        ResponseEntity<Product> product = serviceToTest.createProduct(cleaner2);
        HttpStatus statusCode = product.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        ActiveEnum active = product.getBody().getActive();
        assertEquals(ActiveEnum.OFFLINE, active);
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void givenNotExistingId_WhenUpdateProduct_ThenNull() {
        Mockito.when(mockedProductRepository.findById(3L)).thenReturn(Optional.empty());
        ResponseEntity<Product> productResponseEntity = serviceToTest.updateProduct(3L, cleaner2);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingId_WhenUpdateProduct_ThenUpdatedProduct() {
        Mockito.when(mockedProductRepository.findById(1L)).thenReturn(Optional.of(cleaner));
        Mockito.when(mockedProductRepository.save(cleaner2)).thenReturn(cleaner2);
        ResponseEntity<Product> productResponseEntity = serviceToTest.updateProduct(1L, cleaner2);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(0, body.getQuantity());
    }

    @Test
    public void givenNonExistingName_WhenFindProductByName_ThenNull() {
        Mockito.when(mockedProductRepository.findAllByName("Novarto")).thenReturn(Optional.empty());
        ResponseEntity<List<Product>> novarto = serviceToTest.getProductsByName("Novarto");
        HttpStatus statusCode = novarto.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        List<Product> body = novarto.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingName_WhenFindProductByName_ThenProduct() {
        Mockito.when(mockedProductRepository.findAllByName("Cleaner")).thenReturn(Optional.of(List.of(cleaner)));
        ResponseEntity<List<Product>> cleaner1 = serviceToTest.getProductsByName("Cleaner");
        HttpStatus statusCode = cleaner1.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        List<Product> body = cleaner1.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(body.get(0).getName(), "Cleaner");
    }

    @Test
    public void givenExistingName_WhenFindProductByName_ThenProductsWithThisName() {
        Mockito.when(mockedProductRepository.findAllByName("Cleaner"))
                .thenReturn(Optional.of(List.of(cleaner, cleaner2)));
        ResponseEntity<List<Product>> cleaner1 = serviceToTest.getProductsByName("Cleaner");
        HttpStatus statusCode = cleaner1.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        List<Product> body = cleaner1.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(body.get(0).getName(), "Cleaner");
        assertEquals(body.get(1).getName(), "Cleaner2");
    }

    @Test
    public void givenNonDescriptionWord_WhenFindProductByWord_ThenNull() {
        Mockito.when(mockedProductRepository.findAllByDescriptionContaining("Novarto"))
                .thenReturn(Optional.empty());
        ResponseEntity<List<Product>> novarto = serviceToTest.getProductByWord("Novarto");
        HttpStatus statusCode = novarto.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        List<Product> body = novarto.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingDescriptionWord_WhenFindProductByWord_ThenAllProductsWithThisWordInTheDescription() {
        Mockito.when(mockedProductRepository.findAllByDescriptionContaining("Inc"))
                .thenReturn(Optional.of(List.of(cleaner, cleaner2)));
        ResponseEntity<List<Product>> novarto = serviceToTest.getProductByWord("Inc");
        HttpStatus statusCode = novarto.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        List<Product> body = novarto.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(2, body.size());
        assertTrue(body.get(0).getDescription().contains("Inc"));
        assertTrue(body.get(1).getDescription().contains("Inc"));
    }

    @Test
    public void givenNotExistingProductId_WhenAddingProductToCart_ThenNull() {
        Mockito.when(mockedProductRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Product> productResponseEntity = serviceToTest.addProductToCart(2L, 1L);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenNotExistingCartId_WhenAddingProductToCart_ThenNull() {
        Mockito.when(mockedProductRepository.findById(2L)).thenReturn(Optional.of(cleaner));
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Product> productResponseEntity = serviceToTest.addProductToCart(2L, 2L);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenExistingCartAndProductId_WhenAddingProductToCart_ThenProductAdded() {
        Cart cart = new Cart();
        List<Product> productList = new ArrayList<>();
        productList.add(cleaner2);
        cart.setProducts(productList);
        Mockito.when(mockedProductRepository.findById(2L)).thenReturn(Optional.of(cleaner));
        Mockito.when(mockedCartRepository.findById(2L)).thenReturn(Optional.of(cart));
        ResponseEntity<Product> productResponseEntity = serviceToTest.addProductToCart(2L, 2L);
        HttpStatus statusCode = productResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Product body = productResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(cleaner, body);
    }
}
