package com.example.digital_shop.service.transaction;

import com.example.digital_shop.domain.dto.TransactionDto;
import com.example.digital_shop.entity.transaction.TransactionEntity;
import com.example.digital_shop.repository.transaction.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    @Override
    @Transactional
    public TransactionEntity save(TransactionDto transactionDto, UUID userId) {
        TransactionEntity transaction = modelMapper.map(transactionDto, TransactionEntity.class);
        transaction.setUserId(userId);
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID id, UUID userId) {
        TransactionEntity transaction = transactionRepository.findTransactionEntitiesById(id);
        if (transaction == null){
            return null;
        }
        if (transaction.getUserId().equals(userId)){
            transactionRepository.deleteById(id);
            return true;
        }
        return null;
    }

    @Override
    public void getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        transactionRepository.findAll(pageable).getContent();
    }
}
