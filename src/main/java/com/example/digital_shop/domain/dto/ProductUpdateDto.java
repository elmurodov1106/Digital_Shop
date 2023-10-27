package com.example.digital_shop.domain.dto;

import lombok.*;
import org.springframework.jmx.export.annotation.ManagedNotifications;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductUpdateDto {
    private String name;
    private String model;

    private Double cost;
    private String productType;
    private Integer amount;
    private String description;
}
