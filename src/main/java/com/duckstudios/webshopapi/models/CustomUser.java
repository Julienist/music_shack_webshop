package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

//public enum Role {
//    ADMIN, CUSTOMER
//}

@Entity(name = "custom_user")
public class CustomUser {

    @Id
    @GeneratedValue
    private long id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "Order")
    @JsonManagedReference
    private Order order;

//    @Enumerated(EnumType.STRING);
//    private Role role;

    public CustomUser(){}

    public CustomUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
