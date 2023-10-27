package com.example.digital_shop.entity.transaction;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.UUID;
@Entity(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionEntity extends BaseEntity {
    private UUID senderCard;
    private UUID receiverId;
    private Timestamp date;
    private UUID userId;
}
