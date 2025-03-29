package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Product product;

    private int quantity;
    private BigDecimal totalPrice;

    public OrderProduct(OrderEntity order, Product product, int quantity, BigDecimal totalPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
