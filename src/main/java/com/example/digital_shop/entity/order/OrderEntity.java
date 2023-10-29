package com.example.digital_shop.entity.order;

import com.example.digital_shop.entity.BaseEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderEntity extends BaseEntity {
    @ManyToOne
    private ProductEntity product;
    private UUID userId;
    private Double cost;
    private Integer amount;
}
