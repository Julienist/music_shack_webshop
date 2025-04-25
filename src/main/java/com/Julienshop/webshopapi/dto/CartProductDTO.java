package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.Cart;
import com.Julienshop.webshopapi.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CartProductDTO {

//    @JsonAlias("product_id")
    private Cart cart;
    private Product product;
    private int quantity;
    private BigDecimal totalPrice;

}
