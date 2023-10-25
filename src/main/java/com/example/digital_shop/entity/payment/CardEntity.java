package com.example.digital_shop.entity.payment;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import java.util.UUID;

@Entity(name = "card_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardEntity extends BaseEntity {
    private String cardName;
    @Column(unique = true,nullable = false)
    private String cardNumber;
    private String expireDate;
    private Double balance = 100000.0;
    private UUID ownerId;
}
