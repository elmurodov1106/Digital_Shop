package com.example.digital_shop.entity.product;

import com.example.digital_shop.entity.BaseEntity;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity(name = "product")
//@DiscriminatorColumn(name = "product")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    private String productType;
    private String model;
    private String name;
    private Double cost;
    private UUID userId;
}





