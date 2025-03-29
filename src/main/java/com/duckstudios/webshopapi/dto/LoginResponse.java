package com.duckstudios.webshopapi.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    public String email;

    public String token;

//    public LoginResponse(String email, String token) {
//        this.email = email;
//        this.token = token;
//    }
}
