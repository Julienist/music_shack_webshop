package com.duckstudios.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {

    @JsonAlias("customuser_id")
    public long customuserId;

    public LocalDateTime orderDate;
    public Enum orderStatus;
    public BigDecimal totalPrice;

    public OrderDTO(long customuserId, LocalDateTime orderDate, Enum orderStatus, BigDecimal totalPrice) {
        this.customuserId = customuserId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
