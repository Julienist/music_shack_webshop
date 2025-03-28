package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "OrderDetails")
    @OneToMany(mappedBy = "Payment")
    @JsonManagedReference
    private long id;

    @OneToOne(mappedBy = "CustomUser")
    @JsonManagedReference
    private CustomUser customUser;

    private LocalDateTime orderDate;

    private Enum orderStatus;

    private BigDecimal totalPrice;

    public Order(){}

    public Order(CustomUser customUser, LocalDateTime orderDate, Enum orderStatus, BigDecimal totalPrice) {
        this.customUser = customUser;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Enum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Enum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
