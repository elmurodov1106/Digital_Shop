package com.example.digital_shop.repository;

import com.example.digital_shop.entity.verification.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
  Optional<VerificationCode> findVerificationCodeByUserId(UUID userId);
  void deleteVerificationCodeByUserEmail(String email);
 // Boolean deleteVerificationCodeByUser(UserEntity user);
}
