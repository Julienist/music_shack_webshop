package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.Cart;
import com.Julienshop.webshopapi.models.Product;
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
