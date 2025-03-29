package com.duckstudios.webshopapi.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentDTO {

    private long orderId;
    private String paymentMethod;
    private String paymentDate;

}
