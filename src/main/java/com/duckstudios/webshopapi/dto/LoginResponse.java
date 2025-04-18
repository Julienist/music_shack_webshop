package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
    private Role role;
}
