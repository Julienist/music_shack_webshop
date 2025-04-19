package com.Julienshop.webshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAccountDTO {

        private String email;
        private String password;

}
