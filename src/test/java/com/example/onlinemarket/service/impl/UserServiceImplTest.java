package com.example.onlinemarket.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.User;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.UserRepository;
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
class UserServiceImplTest {
    @Mock
    private UserRepository mockedUserRepository;

    @Mock
    private CartRepository mockedCartRepository;

    private UserServiceImpl serviceToTest;
    private Cart cart = new Cart();

    private final User goshoUpdated =
            new User("gopeto2", "12345", "Goshko2", "goshkobmw2@gmail.com", "0896322101", "Liulin 2", "At the door");
    private final User gosho =
            new User("gopeto", "12345", "Goshko", "goshkobmw@gmail.com", "0896392101", "Liulin 5", "At the door");

    @BeforeEach
    void init() {
        serviceToTest = new UserServiceImpl(mockedUserRepository, mockedCartRepository);
    }

    @Test
    public void givenUser_WhenCreateUser_ThenUserCreated() {
        Mockito.when(mockedUserRepository.save(gosho)).thenReturn(gosho);
        ResponseEntity<User> user = serviceToTest.createUser(gosho);
        HttpStatus statusCode = user.getStatusCode();
        User body = user.getBody();
        HttpStatus expectedStatusCode = HttpStatus.CREATED;
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(gosho, body);
    }

    @Test
    public void givenNewUserData_WhenUpdatingUser_ThenUpdatedUser() {

        Mockito.when(mockedUserRepository.findById(1L)).thenReturn(Optional.of(gosho));
        ResponseEntity<User> userResponseEntity = serviceToTest.updateUserData(1L, goshoUpdated);
        HttpStatus statusCode = userResponseEntity.getStatusCode();
        User body = userResponseEntity.getBody();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        assertEquals(expectedStatusCode, statusCode);
        assertEquals(body.getContactName(), "Goshko2");
        assertEquals(body.getUsername(), "gopeto2");
    }

    @Test
    public void givenNonExistingUserId_WhenUpdatingUser_ThenNull() {
        Mockito.when(mockedUserRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<User> userResponseEntity = serviceToTest.updateUserData(2L, goshoUpdated);
        HttpStatus statusCode = userResponseEntity.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        User body = userResponseEntity.getBody();
        assertEquals(expectedStatusCode, statusCode);
        assertNull(body);
    }

    @Test
    public void givenNonExistingUserId_WhenGetUserById_ThenNull() {
        Mockito.when(mockedUserRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<User> userById = serviceToTest.getUserById(2L);
        HttpStatus statusCode = userById.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.NOT_FOUND;
        User body = userById.getBody();
        assertNull(body);
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void givenExistingUserId_WhenGetUserById_ThenCorrectUser() {
        Mockito.when(mockedUserRepository.findById(1L)).thenReturn(Optional.of(gosho));
        ResponseEntity<User> userById = serviceToTest.getUserById(1L);
        HttpStatus statusCode = userById.getStatusCode();
        HttpStatus expectedStatusCode = HttpStatus.OK;
        User body = userById.getBody();
        assertEquals(gosho, body);
        assertEquals(expectedStatusCode, statusCode);
    }
}
