package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LaptopDto {
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "model cannot be empty")
    private String model;
    private Integer battery;
    @NotEmpty(message = "colour cannot be empty")
    private String colour;

    @NotEmpty(message = "weight cannot be empty")
    private double weight;

    @NotEmpty(message = "memory cannot be empty")
    private Integer memory;

    @NotEmpty(message = "Ram cannot be empty")
    private Integer Ram;

    @NotEmpty(message = "ScreenSize cannot be empty")
    private Integer ScreenSize;

    @NotEmpty(message = "Ghz cannot be empty")
    private Integer Ghz;

    @NotEmpty(message = "cost cannot be empty")
    private Double cost;

    @NotEmpty(message = "product type cannot be empty")
    private String productType;
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotNull(message = "amount cannot be null")
    private Integer amount;
}
