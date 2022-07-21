package com.example.onlinemarket.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * This class hold information about the user and methods to get it
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 4, max = 10)
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 4, max = 15)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Size(min = 4, max = 10)
    private String contactName;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(unique = true, nullable = false)
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column()
    private String address;

    @Column
    private String deliveryPreferences;
    //    @OneToMany
    //    @JsonIgnore
    //    private List<Order> ordersList;
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    public User(
            String username,
            String password,
            String contactName,
            String email,
            String phoneNumber,
            String address,
            String deliveryPreferences) {
        this.username = username;
        this.password = password;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.deliveryPreferences = deliveryPreferences;
    }

    public User() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryPreferences() {
        return deliveryPreferences;
    }

    public void setDeliveryPreferences(String deliveryPreferences) {
        this.deliveryPreferences = deliveryPreferences;
    }

    //    public List<Order> getOrdersList() {
    //        return ordersList;
    //    }
    //
    //    public void setOrdersList(List<Order> ordersList) {
    //        this.ordersList = ordersList;
    //    }
}
