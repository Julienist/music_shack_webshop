package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.Role;

public class AuthenticationDTO {

    public String email;

    public String password;

    public Role role;

    public AuthenticationDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
