package com.Julienshop.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
name = "cart_product",
uniqueConstraints = {
    @UniqueConstraint(
        columnNames = { "cart_id", "product_id" }
    )
})
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
    private BigDecimal totalPrice;

    public CartProduct(Cart cart, Product product, int quantity, BigDecimal totalPrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
