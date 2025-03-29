package com.duckstudios.webshopapi.dto;

public class PaymentDTO {

    private long orderId;
    private String paymentMethod;
    private String paymentDate;

    public PaymentDTO(long orderId, String paymentMethod, String paymentDate) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }
}
