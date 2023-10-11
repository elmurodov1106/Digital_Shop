package com.example.digital_shop.service.product;

import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    public ProductEntity add(ProductCreatDto product, UUID userId, Integer amount, MultipartFile image){
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setUserId(userId);
        try {
            productEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            return null;
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
    public Boolean deleteById(UUID productId, UUID userId) {
        ProductEntity productNotFound = productRepository.findProductEntityById(productId);
        if(productNotFound==null){
            return null;
        }
        if (productNotFound.getUserId().equals(userId)) {
            inventoryRepository.deleteByProductIdEquals(productId);
            productRepository.deleteById(productId);
            return true;
        }
        return null;
    }

    @Override
    @Transactional
    public ProductEntity update(ProductCreatDto update, UUID productId, Integer amount, UUID userId,MultipartFile image) throws IOException {
        ProductEntity productEntity = productRepository.findProductEntityById(productId);
        if(productEntity==null){
            return null;
        }
        InventoryEntity inventoryEntity = inventoryRepository.getByProductId(productId);
        if (productEntity.getUserId().equals(userId)) {
            modelMapper.map(update, productEntity);
            inventoryEntity.setProductCount(amount);
            inventoryRepository.save(inventoryEntity);
            productEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            return productRepository.save(productEntity);
        }
        return null;
    }
}
