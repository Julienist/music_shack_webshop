package com.Julienshop.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskDTO {
    private String name;
    private String description;

    @JsonAlias("category_id")
    private long categoryId;

}
