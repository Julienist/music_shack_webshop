package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {

    @JsonAlias("customuser_id")
    public long customUserId;

    public LocalDateTime orderDate;
    public OrderStatus orderStatus;
    public BigDecimal totalPrice;

    public OrderDTO(long customUserId, LocalDateTime orderDate, OrderStatus orderStatus, BigDecimal totalPrice) {
        this.customUserId = customUserId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
