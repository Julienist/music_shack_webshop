package com.duckstudios.webshopapi.models;

import com.duckstudios.webshopapi.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "custom_user")
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderEntity> orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cart> carts = new ArrayList<>();

    public CustomUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CustomUser(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
