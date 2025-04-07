package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.OrderEntity;
import com.duckstudios.webshopapi.models.Product;
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
