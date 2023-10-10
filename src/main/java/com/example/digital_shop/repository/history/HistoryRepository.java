package com.example.digital_shop.repository.history;

import com.example.digital_shop.entity.history.HistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, UUID> {
    List<HistoryEntity> findHistoryEntitiesBySenderCardIdEqualsIgnoreCase(Pageable pageable, UUID senderCardId);
    List<HistoryEntity> findHistoryEntitiesBySenderCardIdEqualsIgnoreCase(UUID senderCardId);

}
