package com.duckstudios.webshopapi.dto;

import com.duckstudios.webshopapi.models.enums.Role;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationDTO {

    public String email;
    public String password;
    public Role role;

}
