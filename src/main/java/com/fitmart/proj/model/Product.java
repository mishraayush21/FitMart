package com.fitmart.proj.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue()
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    private Double price;
    private Double weight;
    private String description;
    private String imageName;
}
