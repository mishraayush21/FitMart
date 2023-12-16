package com.fitmart.proj.dto;

import lombok.Data;

import javax.persistence.*;


@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int categoryId;
    private Double price;
    private Double weight;
    private String description;
    private String imageName;
}
