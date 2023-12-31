package com.example.digital_shop.repository.inventory;

import com.example.digital_shop.entity.inventory.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {
    void deleteInventoryEntityByProductIdContaining(UUID productId);
    InventoryEntity getByProductId(UUID productId);
    void deleteByProductIdEquals(UUID productId);
}
