package com.example.digital_shop.service.transaction;

import com.example.digital_shop.entity.history.HistoryEntity;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.exception.InsufficientBalanceException;
import com.example.digital_shop.repository.history.HistoryRepository;
import com.example.digital_shop.repository.payment.CardRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final HistoryRepository historyRepository;
    private final CardRepository cardRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void transferMoney(UUID senderAccountId, UUID receiverAccountId, double amount, UUID productId) {
        CardEntity receiverCard = cardRepository.findCardEntityById(receiverAccountId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));
        CardEntity senderCard = cardRepository.findCardEntityById(senderAccountId)
                .orElseThrow(() -> new DataNotFoundException("Card not found"));

        ProductEntity product = productRepository.findProductEntityById(productId);
        if(!product.getUserId().equals(receiverCard.getOwnerId())){
            throw new DataNotFoundException("Receiver card not found");
        }
        if (senderCard.getAmount() < amount) {
            throw new InsufficientBalanceException("Amount not found");
        }
        updateAccountBalance(senderCard.getId(), senderCard.getAmount() - amount);
        updateAccountBalance(receiverCard.getId(), receiverCard.getAmount() + amount);
        HistoryEntity history = HistoryEntity.builder()
                .paymentAmount(amount)
                .receiverCardId(receiverCard.getId())
                .senderCardId(senderCard.getId())
                .productId(product.getId())
                .build();
        historyRepository.save(history);
    }

    @Override
    @Transactional
    public void updateAccountBalance(UUID accountId, double newBalance) {
        CardEntity card = cardRepository.findCardEntityById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Not found"));
        card.setAmount(newBalance);
        cardRepository.save(card);
    }
}
