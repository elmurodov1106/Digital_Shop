package com.example.digital_shop.entity.payment;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.util.UUID;

@Entity(name = "card_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardEntity extends BaseEntity {
    private String cardNumber;
    private String expireDate;
    private Double amount;
    private UUID ownerId;
    @Enumerated(value = EnumType.STRING)
    private CardType cardType;

}
