package com.duckstudios.webshopapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne()

}
