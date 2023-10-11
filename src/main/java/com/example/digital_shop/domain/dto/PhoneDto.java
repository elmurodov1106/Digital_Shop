package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhoneDto {

    @NotEmpty(message = "colour cannot be empty")
    private String colour;

    @NotEmpty(message = "size cannot be empty")
    private String size;

    @NotEmpty(message = "weight cannot be empty")
    private double weight;

    @NotEmpty(message = "memory cannot be empty")
    private Integer memory;

    @NotEmpty(message = "Ram cannot be empty")
    private Integer Ram;

    @NotEmpty(message = "battery cannot be empty")
    private double battery;

    @NotEmpty(message = "FrontCamera cannot be empty")
    private Integer FrontCamera;

    @NotEmpty(message = "BackCamera cannot be empty")
    private Integer BackCamera;

    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "model cannot be empty")
    private String model;

    @NotEmpty(message = "cost cannot be empty")
    private Double cost;

    private String productType;
}
