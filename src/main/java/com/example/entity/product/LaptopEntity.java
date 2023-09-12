package com.example.entity.product;

import com.example.entity.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "laptop")
//@DiscriminatorColumn(name = "laptop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LaptopEntity extends BaseEntity {
    private String colour;
    private Double weight;
    private Integer memory;
    private Integer Ram;
    private Integer ScreenSize;
    private Integer Ghz;
    private String model;
    private String name;
    private Double cost;
}
