package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDTO {

//    @JsonAlias("customuser_id")
//    public long customUserId;

//    private String email; DEZE WIL JE IN DE OrderDAO uit de customuser halen, vanuit securitycontext.

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private List<OrderProductDTO> orderProducts;
}