package com.example.digital_shop.domain.dto;

import com.example.digital_shop.entity.payment.CardEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
@Getter
@Setter
@Builder
public class TransactionDto {
    @NotBlank(message = "Card number not entered")
    private CardEntity senderCard;
    @NotEmpty(message = "Date cannot be empty")
    private Timestamp date;
}
