package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.OrderEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentDTO {

    @JsonAlias("order_id")
    private OrderEntity order;
    private String paymentMethod;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;

}
