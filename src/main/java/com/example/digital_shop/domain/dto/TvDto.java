package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TvDto {

    @NotEmpty(message = "isSmart cannot be empty")
    private Boolean isSmart;

    @NotEmpty(message = "Size cannot be empty")
    private Integer size;

    @NotEmpty(message = "ScreenSpeed cannot be empty")
    private Integer ScreenSpeed;
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "model cannot be empty")
    private String model;

    @NotEmpty(message = "cost cannot be empty")
    private Double cost;

    private String productType;
    private String description;
    @NotNull(message = "amount cannot be null")
    private Integer amount;
}
