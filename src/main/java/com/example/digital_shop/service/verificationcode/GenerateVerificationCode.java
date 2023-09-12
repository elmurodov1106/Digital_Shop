package com.example.digital_shop.service.verificationcode;

import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.entity.verification.VerificationCode;

public interface GenerateVerificationCode {
    VerificationCode generateVerificationCode(UserEntity user);
}
