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
name= "order_product",
uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"order_id","product_id"}
    )
})
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name= "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name= "product_id", nullable = false)
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
