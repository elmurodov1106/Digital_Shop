package com.example.digital_shop.entity.product;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    protected String productType;
    protected String model;
    protected String name;
    protected Double cost;
    protected UUID userId;
    @Column(length = 1000000000)
    protected String image;
    private Integer amount;
    private String description;
}





