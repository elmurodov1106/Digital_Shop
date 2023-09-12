package com.example.digital_shop.repository.payment;

import com.example.digital_shop.entity.payment.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {

}
