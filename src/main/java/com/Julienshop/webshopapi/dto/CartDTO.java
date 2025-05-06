package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartDTO {

    private List<CartProduct> cartProducts;

//    @JsonAlias("custom_user")
//    private CustomUser customUser;
    private boolean isActive;

}
