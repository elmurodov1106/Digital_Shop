package com.example.digital_shop.entity.history;

import com.example.digital_shop.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import java.util.UUID;

@Entity(name = "history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HistoryEntity extends BaseEntity {
    private UUID sender;
    private UUID receiver;
    private String productName;
    private Integer amount;
    private Double cost;
}
