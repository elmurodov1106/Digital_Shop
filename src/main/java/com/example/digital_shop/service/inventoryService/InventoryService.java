package com.example.digital_shop.service.inventoryService;

import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryEntity add(InventoryCreateDto inventory);
    List<InventoryEntity> getAll(int size, int page);
    //    List<InventoryEntity> search(int page,int size,UUID productId);
    void deleteByInventoryId(UUID inventoryId);
    void deleteByProductId(UUID productId);
    InventoryEntity update(InventoryCreateDto update,UUID id,UUID productId);

    ProductEntity getByProductId(UUID productId);
}
