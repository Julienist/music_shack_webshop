package com.Julienshop.webshopapi.models;

import com.Julienshop.webshopapi.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(name = "\"Order\"")
public class OrderEntity {
    @Id
    @GeneratedValue
    private long id;

//    @OneToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private Payment payment;
    //payment via 1:M relatie erin zetten via orderId.

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="customUser_id", nullable = false)
    @JsonBackReference
    private CustomUser customUser;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderProduct> orderProducts;

    public OrderEntity(CustomUser customUser, LocalDateTime orderDate, OrderStatus orderStatus, BigDecimal totalPrice, List<OrderProduct> orderProducts) {
        this.customUser = customUser;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderProducts = orderProducts;
    }

    public OrderEntity(CustomUser customUser, LocalDateTime orderDate, OrderStatus orderStatus, BigDecimal totalPrice) {
        this.customUser = customUser;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
