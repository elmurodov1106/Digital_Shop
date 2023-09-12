package com.example.digital_shop.entity.verification;


import com.example.digital_shop.entity.BaseEntity;
import com.example.digital_shop.entity.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "verification_code")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationCode extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    private String sendingCode;
    private LocalDateTime expiryDate;
}
