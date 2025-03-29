package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
public class OrderDTO {

    @JsonAlias("customuser_id")
    public long customUserId;

    public LocalDateTime orderDate;
    public OrderStatus orderStatus;
    public BigDecimal totalPrice;

}
