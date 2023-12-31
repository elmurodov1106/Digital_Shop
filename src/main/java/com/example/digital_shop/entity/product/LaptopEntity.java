package com.example.digital_shop.entity.product;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "laptop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LaptopEntity extends ProductEntity {
    private Integer battery;
    private String colour;
    private Double weight;
    private Integer memory;
    private Integer Ram;
    private Integer ScreenSize;
    private Integer Ghz;
}
