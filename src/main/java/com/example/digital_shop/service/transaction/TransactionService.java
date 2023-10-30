package com.example.digital_shop.service.transaction;

import java.util.UUID;

public interface TransactionService {
    String transferMoney(UUID senderAccountId, UUID orderId);
}
