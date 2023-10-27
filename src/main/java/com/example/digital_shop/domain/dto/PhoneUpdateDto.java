package com.example.digital_shop.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhoneUpdateDto {
    private String colour;
    private String size;
    private Double weight;
    private Integer memory;
    private Integer Ram;
    private Double battery;
    private Integer FrontCamera;
    private Integer BackCamera;
    private String description;
    private String name;
    private String model;
    private Double cost;
    private Integer amount;
}
