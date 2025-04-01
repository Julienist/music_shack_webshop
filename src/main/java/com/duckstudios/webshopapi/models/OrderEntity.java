package com.duckstudios.webshopapi.models;

import com.duckstudios.webshopapi.models.enums.OrderStatus;
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
@Table(name = "\"Order\"")
public class OrderEntity {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderProduct> orderProducts;

//    @OneToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private Payment payment;
    //payment via 1:M relatie erin zetten via orderId.

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonBackReference
    private CustomUser customUser;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;

    public OrderEntity(CustomUser customUser, LocalDateTime orderDate, OrderStatus orderStatus, BigDecimal totalPrice) {
        this.customUser = customUser;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public CustomUser getCustomUser() {
//        return customUser;
//    }
//
//    public void setCustomUser(CustomUser customUser) {
//        this.customUser = customUser;
//    }
//
//    public LocalDateTime getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(LocalDateTime orderDate) {
//        this.orderDate = orderDate;
//    }
//
//    public Enum getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(Enum orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//}
