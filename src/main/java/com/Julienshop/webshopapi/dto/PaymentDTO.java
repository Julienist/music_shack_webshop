package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.OrderEntity;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
public class PaymentDTO {

    @JsonAlias("order_id")
    public OrderEntity order;
    public String paymentMethod;
    public BigDecimal paymentAmount;
    public LocalDateTime paymentDate;

}
