package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonBackReference
//    private Long orderId;

    private String paymentMethod;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;

    public Payment(Long orderId, String paymentMethod, BigDecimal paymentAmount, LocalDateTime paymentDate) {
//        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }

    // orderId hierin geintegreerd te krijgen met M:1 relatie.

}
