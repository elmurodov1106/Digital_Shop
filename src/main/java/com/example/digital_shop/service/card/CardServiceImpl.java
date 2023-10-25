package com.example.digital_shop.service.card;

import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CardEntity add(CardCreatedDto card, UUID ownerId) {
        CardEntity cardEntity1 = cardRepository.findCardEntityByCardNumberEqualsIgnoreCase(card.getCardNumber());
        if (cardEntity1 == null) {
            CardEntity cardEntity = modelMapper.map(card, CardEntity.class);
            cardEntity.setOwnerId(ownerId);
            return cardRepository.save(cardEntity);
        }
        return null;
    }

    @Override
    public List<CardEntity> getAllUserCards(int size, int page,UUID userId) {
        Pageable pageable = PageRequest.of(page, size);
        List<CardEntity> cards = cardRepository.findCardEntitiesByOwnerId(pageable,userId);
        if(cards.isEmpty()){
            throw new DataNotFoundException("Card not found");
        }
       return cards;
    }

    @Override
    public List<CardEntity> findCardEntityByOwnerId(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return cardRepository.findCardEntityByOwnerId(userId);
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID cardId, UUID ownerId) {
        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwnerId().equals(ownerId)) {
            cardRepository.deleteById(cardId);
            return true;
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    @Transactional
    public CardEntity update(String name, UUID cardId, UUID ownerId) {
        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwnerId().equals(ownerId)) {
            cardEntity.setCardName(name);
            return cardRepository.save(cardEntity);
        }
        throw new DataNotFoundException("User not found");
    }


}
