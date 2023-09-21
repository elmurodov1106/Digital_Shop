package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCreatDto {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "model cannot be empty")
    private String model;

    @NotEmpty(message = "cost cannot be empty")
    private Double cost;

    private String productType;
}
