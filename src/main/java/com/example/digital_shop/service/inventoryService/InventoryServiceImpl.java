package com.example.digital_shop.service.inventoryService;

import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public InventoryEntity add(InventoryCreateDto inventory) {
        InventoryEntity inventoryEntity = modelMapper.map(inventory, InventoryEntity.class);
        return inventoryRepository.save(inventoryEntity);
    }

    @Override
    public List<InventoryEntity> getAll(int size, int page) {
        Pageable pageable = PageRequest.of(page,size);
        return inventoryRepository.findAll(pageable).getContent();
    }


    @Override
    public void deleteByInventoryId(UUID id) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Inventory not found"));

        inventoryRepository.deleteById(inventoryEntity.getId());
    }

    @Override
    public void deleteByProductId(UUID productId) {
        inventoryRepository.deleteInventoryEntityByProductIdContaining(productId);
    }

    @Override
    public InventoryEntity update(InventoryCreateDto update, UUID id, UUID productId) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Inventory not found"));
        if (inventoryEntity.getProductId().equals(productId)){
            modelMapper.map(update,inventoryEntity);
            return inventoryRepository.save(inventoryEntity);
        }
        throw new DataNotFoundException("Product not found");
    }
    @Override
    public InventoryEntity getByProductId(UUID productId) {
        return inventoryRepository.getByProductId(productId);
    }
}
