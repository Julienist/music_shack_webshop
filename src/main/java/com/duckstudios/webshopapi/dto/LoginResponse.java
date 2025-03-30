package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.Role;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    public String email;
    public String token;
    public Role role;
}
