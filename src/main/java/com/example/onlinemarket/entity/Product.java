package com.example.onlinemarket.entity;

import com.example.onlinemarket.ActiveEnum;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * This class holds values for name,code,price,quantity,description and if the product is active and methods to get it
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 15)
    private String name;

    @Size(min = 3)
    private String description;

    @Column(nullable = false)
    @Min(value = 0L)
    private double price;

    @Column(nullable = false)
    @Min(value = 0)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ActiveEnum active;

    public Product(String name, String description, double price, int quantity, ActiveEnum active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
    }

    public Product() {
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ActiveEnum getActive() {
        return active;
    }

    public void setActive(ActiveEnum active) {
        this.active = active;
    }
}
