package com.duckstudios.webshopapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private long id;

    private String name;

//    @OneToMany(mappedBy = "category")
//    @JsonManagedReference
//    private List<Task> tasks;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public Category(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Task> getTasks() {
//        return tasks;
//    }

//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
