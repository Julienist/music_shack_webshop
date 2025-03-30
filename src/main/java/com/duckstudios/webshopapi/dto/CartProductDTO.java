package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.Cart;
import com.duckstudios.webshopapi.models.Product;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class CartProductDTO {

//    @JsonAlias("product_id")
    public Cart cart;
    public Product product;
    public int quantity;
    public BigDecimal totalPrice;

}
