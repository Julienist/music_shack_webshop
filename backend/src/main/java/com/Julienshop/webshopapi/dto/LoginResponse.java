package com.Julienshop.webshopapi.dto;

import com.Julienshop.webshopapi.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
    private Role role;
}
