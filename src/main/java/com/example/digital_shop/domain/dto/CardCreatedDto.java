package com.example.digital_shop.domain.dto;

import com.example.digital_shop.entity.payment.CardType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardCreatedDto {

    @NotBlank(message = "Card number not entered")
    private String card_number;

    @NotBlank(message = "Expire date not entered")
    private String expire_date;

    @NotBlank(message = "Card type not entered")
    private CardType card_type;

}
