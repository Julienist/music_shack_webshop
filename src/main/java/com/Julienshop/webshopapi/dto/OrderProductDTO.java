package com.Julienshop.webshopapi.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderProductDTO {

    private Long productId;
    private int quantity;
    private BigDecimal totalPrice;

}
