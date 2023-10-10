package com.example.digital_shop.service.history;

import com.example.digital_shop.entity.history.HistoryEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public List<HistoryEntity> getSenderCardHistory(int size, int page, UUID senderCardId) {
        Pageable pageable = PageRequest.of(size,page);
        List<HistoryEntity> historyEntities =
                historyRepository.findHistoryEntitiesBySenderCardIdEqualsIgnoreCase(pageable,senderCardId);
        if (historyEntities.isEmpty()){
            throw new DataNotFoundException("User History not found");
        }
        return historyEntities;
    }

    @Override
    public List<HistoryEntity> getAllHistories(int size, int page) {
        Pageable pageable = PageRequest.of(size, page);
        List<HistoryEntity> history = historyRepository.findAll(pageable).getContent();
        if (history.isEmpty()){
            throw new DataNotFoundException("History not found");
        }
        return history;
    }
}
