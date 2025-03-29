package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private String imageurl;

    @Version
    private int version;
    private long amountInStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderProduct> orderProducts;

    public Product(String name, String description, BigDecimal price, boolean isAvailable, String imageurl, long amountInStock, Category category, List<OrderProduct> orderProducts) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageurl = imageurl;
        this.amountInStock = amountInStock;
        this.category = category;
        this.orderProducts = orderProducts;
    }
}
