package com.example.digital_shop.service.payment;

import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.payment.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CardEntity add(CardCreatedDto card, UUID ownerId) {
        CardEntity cardEntity = modelMapper.map(card, CardEntity.class);
        cardEntity.setOwnerId(ownerId);
       return cardRepository.save(cardEntity);
    }

    @Override
    public List<CardEntity> getAllUserCards(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<CardEntity> cards = cardRepository.findAll(pageable).getContent();
        if(cards.isEmpty()){
            throw new DataNotFoundException("Card not found");
        }
        return cards;
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID cardId, UUID ownerId) {
        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwnerId().equals(ownerId)) {
            cardRepository.deleteById(cardId);
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    @Transactional
    public CardEntity update(CardCreatedDto update, UUID cardId, UUID ownerId) {
        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwnerId().equals(ownerId)) {
            modelMapper.map(update, cardEntity);
            return cardRepository.save(cardEntity);
        }
        throw new DataNotFoundException("User not found");
    }


}
