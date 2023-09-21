package com.example.digital_shop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TvDto {

    @NotEmpty(message = "isSmart cannot be empty")
    private Double isSmart;

    @NotEmpty(message = "Size cannot be empty")
    private Integer Size;

    @NotEmpty(message = "ScreenSpeed cannot be empty")
    private Integer ScreenSpeed;
}
