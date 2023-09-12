package com.example.digital_shop.service.product;


import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import com.example.digital_shop.service.inventoryService.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional
    public ProductEntity add(ProductCreatDto product, UUID userId, Integer amount) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setUserId(userId);
        ProductEntity savedProduct = productRepository.save(productEntity);
        InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
        inventoryCreateDto.setProductId(savedProduct.getId());
        inventoryCreateDto.setProductCount(amount);
        InventoryEntity inventoryEntity = modelMapper.map(inventoryCreateDto, InventoryEntity.class);
        inventoryRepository.save(inventoryEntity);
        return savedProduct;
    }

    @Override
    public List<ProductEntity> getAllProducts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> content = productRepository.findAll(pageable).getContent();
        if (content.isEmpty()) {
            throw new DataNotFoundException("Products not found");
        }
        return content;
    }

    @Override
    public List<ProductEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.searchProductEntitiesByModelContainingIgnoreCase(model, pageable);
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID productId, UUID userId, String token) {
        ProductEntity productNotFound = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        if (productNotFound.getUserId().equals(userId)) {
            inventoryRepository.deleteByProductIdEquals(productId);
            productRepository.deleteById(productId);
            return true;
        }
        throw new DataNotFoundException("Product not found");
    }

    @Override
    @Transactional
    public ProductEntity update(ProductCreatDto update, UUID productId, Integer amount, UUID userId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        InventoryEntity inventoryEntity = inventoryRepository.getByProductId(productId);
        if (productEntity.getUserId().equals(userId)) {
            modelMapper.map(update, productEntity);
            inventoryEntity.setProductCount(amount);
            inventoryRepository.save(inventoryEntity);
            return productRepository.save(productEntity);
        }
        throw new DataNotFoundException("Product not found");
    }
}
