package com.example.digital_shop.repository.payment;

import com.example.digital_shop.entity.payment.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
}
