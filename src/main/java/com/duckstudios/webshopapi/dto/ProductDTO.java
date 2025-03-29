package com.duckstudios.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ProductDTO {
    public String name;
    public String description;
    public BigDecimal price;
    public boolean isAvailable;
    public String imageurl;
    public long amountInStock;

    @JsonAlias("category_id")
    public long categoryId;

//    public List<OrderProduct> orderProducts;
    // weet niet of bovenstaande in constructor moet.

}
