package com.Julienshop.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageurl;
    private long amountInStock;

    @JsonAlias("category_id")
    private long categoryId;

//    public List<OrderProduct> orderProducts;
    // weet niet of bovenstaande in constructor moet.

}
