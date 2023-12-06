package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;
@Getter
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String image;
    private String description;
    private String color;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private  Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Product(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Product() {

    }

    public Product(String color, String image) {
        this.color = color;
        this.image = image;
    }

    public Product(String name, Integer price, Integer quantity, String image, String description, String color, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.color = color;
        this.category = category;
    }

    public Product(String name, Integer price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(Integer id,String name, Integer price, Integer quantity, String description, String color,String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.color = color;
        this.image = image;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id +
                ", \"name\": \"" + name.toString().trim().replace("\n", "") + "\"" +
                ", \"price\": " + price +
                ", \"quantity\": " + quantity +
                ", \"image\": \"" + image + "\"" +
                "}";
    }
}

