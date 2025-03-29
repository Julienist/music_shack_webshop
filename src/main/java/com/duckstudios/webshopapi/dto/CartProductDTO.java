package com.duckstudios.webshopapi.dto;

public class CartProductDTO {

    private long productId;
    private int quantity;

    public CartProductDTO(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
