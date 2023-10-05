package com.example.digital_shop.repository.transaction;

import com.example.digital_shop.entity.transaction.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    TransactionEntity findTransactionEntitiesById(UUID id);
}
