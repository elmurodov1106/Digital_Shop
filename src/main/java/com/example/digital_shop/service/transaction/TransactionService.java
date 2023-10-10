package com.example.digital_shop.service.transaction;

import java.util.UUID;

public interface TransactionService {
    void transferMoney(UUID senderAccountId, UUID receiverAccountId, double amount,UUID productId);
    void updateAccountBalance(UUID accountId, double newBalance);
}
