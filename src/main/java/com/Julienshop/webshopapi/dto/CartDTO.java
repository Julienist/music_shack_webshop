package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.CartProduct;
import com.Julienshop.webshopapi.models.CustomUser;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CartDTO {

    public List<CartProduct> cartProducts;

    @JsonAlias("custom_user")
    public CustomUser customUser;
    public boolean isActive;

}
