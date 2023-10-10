package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Builder
public class HistoryDto {
    @NotBlank(message = "SenderCardId not entered")
    private UUID senderCardId;
    @NotBlank(message = "ReceiverCardId not entered")
    private UUID receiverCardId;
    @NotBlank(message = "ProductId not entered")
    private UUID productId;
    @NotBlank(message = "PaymentAmount not entered")
    private Double paymentAmount;
}
