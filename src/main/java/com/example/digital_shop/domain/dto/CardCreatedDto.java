package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardCreatedDto {
    @NotBlank(message = "Card name not entered")
    private String name;
    @NotBlank(message = "Card number not entered")
    @Pattern(regexp = "\\d{16}")
    private String number;
    @NotBlank(message = "Expire date not entered")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(\\d{2})$")
    private String expireDate;
    @NotBlank(message = "Balance not entered")
    @Null(message = "Balance is Null")
    private Double balance;


}
