package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.OrderEntity;
import com.Julienshop.webshopapi.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderProductDTO {

    private OrderEntity order;
    private Product product;
//    private Long productId;
    private int quantity;
    private BigDecimal totalPrice;

}
