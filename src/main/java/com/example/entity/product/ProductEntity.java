package com.example.entity.product;

import com.example.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;


@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    private String productType;
    private String model;
    private String name;
    private Double cost;
}





