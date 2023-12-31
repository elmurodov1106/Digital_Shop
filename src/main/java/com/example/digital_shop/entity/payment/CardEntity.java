package com.example.digital_shop.entity.payment;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity(name = "card_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardEntity extends BaseEntity {
    private String name;
    @Column(unique = true,nullable = false)
    private String number;
    private String expireDate;
    @ColumnDefault("100000.0")
    private Double balance;
    private UUID ownerId;
}
