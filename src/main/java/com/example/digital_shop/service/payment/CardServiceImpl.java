package com.example.digital_shop.service.payment;

import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.payment.CardRepository;
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
    public CardEntity add(CardCreatedDto card, UUID owner_id) {
        CardEntity cardEntity = modelMapper.map(card, CardEntity.class);
        cardEntity.setOwner_id(owner_id);
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
    public Boolean deleteById(UUID card_id, UUID owner_id) {
        CardEntity cardEntity = cardRepository.findById(card_id)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwner_id().equals(owner_id)) {
            cardRepository.deleteById(card_id);
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    public CardEntity update(CardCreatedDto update, UUID card_id, UUID owner_id) {
        CardEntity cardEntity = cardRepository.findById(card_id)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        if (cardEntity.getOwner_id().equals(owner_id)) {
            modelMapper.map(update, cardEntity);
            return cardRepository.save(cardEntity);
        }
        throw new DataNotFoundException("User not found");
    }


}
