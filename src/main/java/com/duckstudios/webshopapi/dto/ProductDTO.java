package com.duckstudios.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public class ProductDTO {
    public String name;
    public String description;
    public BigDecimal price;
    public boolean isAvailable;
    public String imageurl;
    public long amountInStock;

    @JsonAlias("category_id")
    public long categoryId;

    public ProductDTO(String name, String description, BigDecimal price, boolean isAvailable, String imageurl, long amountInStock, long categoryid) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageurl = imageurl;
        this.amountInStock = amountInStock;
        this.categoryId = categoryid;
    }
}
