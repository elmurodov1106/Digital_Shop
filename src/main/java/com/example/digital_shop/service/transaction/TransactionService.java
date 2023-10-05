package com.example.digital_shop.service.transaction;

import com.example.digital_shop.domain.dto.TransactionDto;
import com.example.digital_shop.entity.transaction.TransactionEntity;

import java.util.UUID;

public interface TransactionService {
    TransactionEntity save(TransactionDto transactionDto,UUID userId);
    Boolean deleteById(UUID id, UUID userId);
    void getAllTransactions(int page, int size);
}
