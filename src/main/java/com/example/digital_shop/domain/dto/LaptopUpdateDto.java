package com.example.digital_shop.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LaptopUpdateDto {
    private String name;
    private String model;
    private Integer battery;
    private String colour;
    private Double weight;
    private Integer memory;
    private Integer Ram;
    private Integer ScreenSize;
    private Integer Ghz;
    private Double cost;
    private String productType;
    private String description;
    private Integer amount;
}
