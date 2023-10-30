package com.example.digital_shop.service.transaction;

import com.example.digital_shop.entity.history.HistoryEntity;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.seller.SellerInfo;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.exception.InsufficientBalanceException;
import com.example.digital_shop.repository.SellerRepository;
import com.example.digital_shop.repository.history.HistoryRepository;
import com.example.digital_shop.repository.order.OrderRepository;
import com.example.digital_shop.repository.payment.CardRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final HistoryRepository historyRepository;
    private final CardRepository cardRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public String transferMoney(UUID senderAccountId, UUID orderId) {
        Optional<CardEntity> senderCard = cardRepository.findCardEntityById(senderAccountId);
        CardEntity card;
        if(senderCard.isPresent()){
            card = senderCard.get();
        }else {
            return null;
        }
        OrderEntity byId = orderRepository.findOrderEntityByIdEquals(orderId);
        ProductEntity product = productRepository.findProductEntityById(byId.getProduct().getId());
        SellerInfo sellerInfo = sellerRepository.findSellerInfoByUserIdEquals(product.getUserId());
        if (card.getBalance() < byId.getCost()) {
            return "insufficient balance";
        }
        card.setBalance(card.getBalance()-byId.getCost());
        sellerInfo.setBalance(sellerInfo.getBalance()+ byId.getCost());
        HistoryEntity history = HistoryEntity.builder()
                .amount(byId.getAmount())
                .receiver(product.getUserId())
                .sender(card.getId())
                .productName(product.getName())
                .cost(byId.getCost())
                .build();
        historyRepository.save(history);
        cardRepository.save(card);
        orderRepository.deleteById(orderId);
        if(Objects.equals(product.getAmount(), byId.getAmount())){
            productRepository.deleteById(product.getId());
        }
        product.setAmount(product.getAmount()- byId.getAmount());
        productRepository.save(product);
        return "Successfully bought";
    }
}
