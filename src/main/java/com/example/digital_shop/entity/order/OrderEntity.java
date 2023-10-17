package com.example.digital_shop.entity.order;

import com.example.digital_shop.entity.BaseEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.user.UserEntity;
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
public class OrderEntity extends BaseEntity {
    @ManyToOne
    private ProductEntity productId;
    @ManyToOne
    private UserEntity userId;
    private Double cost;

    private Double amount;
}
