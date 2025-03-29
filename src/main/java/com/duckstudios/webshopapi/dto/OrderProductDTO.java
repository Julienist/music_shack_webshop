package com.duckstudios.webshopapi.dto;

public class OrderProductDTO {

    private long productId;
    private int quantity;

    public OrderProductDTO(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
