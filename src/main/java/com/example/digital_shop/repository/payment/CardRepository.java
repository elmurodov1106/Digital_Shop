package com.example.digital_shop.repository.payment;

import com.example.digital_shop.entity.payment.CardEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    Optional<CardEntity>findCardEntityById(UUID id);
    List<CardEntity> findCardEntityByOwnerId(UUID userId);
    List<CardEntity> findCardEntitiesByOwnerId(Pageable pageable,UUID ownerId);

    CardEntity findCardEntityByNumberEqualsIgnoreCase(String cardNumber);
}
