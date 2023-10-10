package com.example.digital_shop.service.history;

import com.example.digital_shop.domain.dto.HistoryDto;
import com.example.digital_shop.entity.history.HistoryEntity;
import java.util.List;
import java.util.UUID;

public interface HistoryService {
    List<HistoryEntity> getSenderCardHistory(int size, int page, UUID senderCardId);
    List<HistoryEntity> getAllHistories(int size, int page);
}
