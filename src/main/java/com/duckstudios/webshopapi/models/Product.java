package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageurl;

    @Version
    private int version;
    private long amountInStock;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private Category category;

    public Product(){}

    public Product(String name, String description, BigDecimal price, boolean isAvailable, String imageurl, long amountInStock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageurl = imageurl;
        this.amountInStock = amountInStock;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getStock() {
        return amountInStock;
    }

    public void setStock(long stock) {
        this.amountInStock = stock;
    }
}
