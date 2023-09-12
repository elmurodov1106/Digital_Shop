package com.example.digital_shop.entity.product;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "phone")
//@DiscriminatorColumn(name = "phone")
//@DiscriminatorValue("phone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhoneEntity extends ProductEntity {
    private String colour;
    private String size;
    private double weight;
    private Integer memory;
    private Integer Ram;
    private double battery;
    private Integer FrontCamera;
    private Integer BackCamera;
    private String description;
//    private String model;
//    private String name;
//    private Double cost;
//    private UUID userId;

}
