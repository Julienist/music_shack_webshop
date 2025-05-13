package com.Julienshop.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customUser_id", nullable = false)
    @JsonBackReference
    private CustomUser customUser;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartProduct> cartProducts;

    private boolean isActive;

    public Cart(List<CartProduct> cartProducts, CustomUser customUser, boolean isActive) {
        this.cartProducts = cartProducts;
        this.customUser = customUser;
        this.isActive = true;
    }

    public Cart(List<CartProduct> cartProducts, long customUserId, boolean isActive) {
    }
}
