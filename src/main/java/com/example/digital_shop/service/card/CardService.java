package com.example.digital_shop.service.card;


import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.payment.CardEntity;
import java.util.List;
import java.util.UUID;

public interface CardService {
    CardEntity add(CardCreatedDto card, UUID ownerId);
    List<CardEntity> getAllUserCards(int size, int page);
    Boolean deleteById(UUID cardId, UUID ownerId);
    CardEntity update(String name,UUID cardId,UUID ownerId);
}
