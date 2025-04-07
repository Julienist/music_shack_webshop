package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.OrderStatus;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class OrderDTO {

//    @JsonAlias("customuser_id")
//    public long customUserId;

    public String email;
    public LocalDateTime orderDate;
    public OrderStatus orderStatus;
    public BigDecimal totalPrice;
    public List<OrderProductDTO> orderProducts;
}