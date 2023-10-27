package com.example.digital_shop.service.transaction;

import com.example.digital_shop.entity.history.HistoryEntity;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.seller.SellerInfo;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.exception.InsufficientBalanceException;
import com.example.digital_shop.repository.SellerRepository;
import com.example.digital_shop.repository.history.HistoryRepository;
import com.example.digital_shop.repository.payment.CardRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final HistoryRepository historyRepository;
    private final CardRepository cardRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public String transferMoney(UUID senderAccountId, double amount, UUID productId) {
        Optional<CardEntity> senderCard = cardRepository.findCardEntityById(senderAccountId);
        CardEntity card;
        if(senderCard.isPresent()){
            card = senderCard.get();
        }else {
            return null;
        }
        ProductEntity product = productRepository.findProductEntityById(productId);
        Optional<SellerInfo> seller = sellerRepository.findById(product.getUserId());
        SellerInfo sellerInfo = null;
        if(seller.isPresent()){
           sellerInfo = seller.get();
        }
        if (card.getBalance() < amount) {
            return "insufficient balance";
        }
        updateAccountBalance(card.getId(), card.getBalance() - amount);
        assert sellerInfo != null;
        sellerInfo.setBalance(sellerInfo.getBalance()+ amount);
        HistoryEntity history = HistoryEntity.builder()
                .paymentAmount(amount)
                .receiverId(product.getUserId())
                .senderCardId(card.getId())
                .productId(product.getId())
                .build();
        historyRepository.save(history);
        return "Successfully bought";
    }

    @Override
    @Transactional
    public void updateAccountBalance(UUID accountId, double newBalance) {
        CardEntity card = cardRepository.findCardEntityById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Not found"));
        card.setBalance(newBalance);
        cardRepository.save(card);
    }
}
