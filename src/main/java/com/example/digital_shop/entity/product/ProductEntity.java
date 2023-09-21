package com.example.digital_shop.entity.product;

import com.example.digital_shop.entity.BaseEntity;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    protected String image;
}





