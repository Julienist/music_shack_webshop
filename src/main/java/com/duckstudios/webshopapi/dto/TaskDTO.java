package com.duckstudios.webshopapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskDTO {
    public String name;
    public String description;

    @JsonAlias("category_id")
    public long categoryId;

}
