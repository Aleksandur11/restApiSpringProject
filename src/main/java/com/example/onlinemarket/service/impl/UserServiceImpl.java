package com.example.onlinemarket.service.impl;

import com.example.onlinemarket.entity.Cart;
import com.example.onlinemarket.entity.User;
import com.example.onlinemarket.repository.CartRepository;
import com.example.onlinemarket.repository.UserRepository;
import com.example.onlinemarket.service.UserService;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class holds methods for getting and manipulating user data
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * This method creates an initial user in the database
     */
    @Override
    @Transactional
    public void initializeUsers() {
        if (userRepository.count() == 0) {
            User gosho = new User(
                    "gopeto", "12345", "Goshko", "goshkobmw@gmail.com", "0896392101", "Liulin 5", "At the door");

            User save = userRepository.save(gosho);
            Cart cart = new Cart();
            cart.setUser(save);
            save.setCart(cart);
            cartRepository.save(cart);
        }
    }

    /**
     * This method creates the given use in the database
     *
     * @param user User object to be created in the database
     * @return ResponseEntity with the created user and status:Created
     */
    @Override
    public ResponseEntity<User> createUser(User user) {
        Cart cart = new Cart();
        user.setCart(cart);
        User createdUser = userRepository.save(user);
        cart.setUser(createdUser);
        cartRepository.save(cart);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * This method updates the user data
     *
     * @param id   the id of the user we want to update
     * @param user the User with the new data we want to update
     * @return ResponseEntity with null body and status:Not found if there isn't user with the given id in the database or
     * returns ResponseEntity with the updated user and status:OK
     */
    @Override
    public ResponseEntity<User> updateUserData(long id, User user) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        User user1 = oldUser.get();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setAddress(user.getAddress());
        user1.setDeliveryPreferences(user.getDeliveryPreferences());
        user1.setContactName(user.getContactName());
        user1.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user1);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    /**
     * This method returns a user by the given id
     *
     * @param id the id of the wanted user
     * @return ResponseEntity with null body and status:Not found if the user with the given id doesn't exist in the database or
     * ResponseEntity with the user and status:Ok
     */
    @Override
    public ResponseEntity<User> getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
