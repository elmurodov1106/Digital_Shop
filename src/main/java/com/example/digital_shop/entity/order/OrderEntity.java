package com.example.digital_shop.entity.order;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntity extends BaseEntity {

    private UUID productId;

    private UUID userId;

    private Double cost;

    private Double amount;
}
