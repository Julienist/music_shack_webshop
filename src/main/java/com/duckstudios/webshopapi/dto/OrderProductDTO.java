package com.duckstudios.webshopapi.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class OrderProductDTO {

    //    public OrderEntity order;
//    public Product product;
    public Long productId;
    public int quantity;
    public BigDecimal totalPrice;

}
