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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartProduct> cartProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CustomUser customUser;

    private boolean isActive;

    public Cart(List<CartProduct> cartProducts, CustomUser customUser, boolean isActive) {
        this.cartProducts = cartProducts;
        this.customUser = customUser;
        this.isActive = isActive;
    }

    public Cart(List<CartProduct> cartProducts, long customUserId, boolean isActive) {
    }
}
