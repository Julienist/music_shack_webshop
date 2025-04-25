package com.Julienshop.webshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderProductDTO {

    //    public OrderEntity order;
//    public Product product;
    private Long productId;
    private int quantity;
    private BigDecimal totalPrice;

}
