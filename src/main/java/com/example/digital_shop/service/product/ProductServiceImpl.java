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
    private final InventoryService inventoryService;


    @Transactional
    public ProductEntity add(ProductCreatDto product, UUID userId, Integer amount, String token) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setUserId(userId);
        ProductEntity savedProduct = productRepository.save(productEntity);
        InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
        inventoryCreateDto.setProductId(savedProduct.getId());
        inventoryCreateDto.setProductCount(amount);
        inventoryRepository.add(inventoryCreateDto);
        return savedProduct;
    }


    public List<ProductEntity> getAllProducts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> content = productRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Products not found");
        }
        return content;
    }

    public List<ProductEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.searchProductEntitiesByModelContainingIgnoreCase(model, pageable);
    }


    public Boolean deleteById(UUID productId, UUID userId,String token) {
        ProductEntity productNotFound = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        if (productNotFound.getUserId().equals(userId)) {
            productRepository.deleteById(productId);
            inventoryRepository.deleteByProductId(productId);
            return true;
        }
        throw new DataNotFoundException("Product not found");
    }


    public ProductEntity update(ProductCreatDto update, UUID productId,Integer amount, UUID userId,String token) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        ProductEntity product = inventoryRepository.getByProductId(productId);
        if (productEntity.getUserId().equals(userId)) {
            modelMapper.map(update, productEntity);
            InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
            inventoryCreateDto.setProductId(userId);
            inventoryCreateDto.setProductCount(amount);
           InventoryEntity inventoryEntity= inventoryRepository.getByProductId(productId);
            inventoryService.update(inventoryCreateDto,)
            return productRepository.save(productEntity);
        }
        throw new DataNotFoundException("Product not found");
    }
//    public void addInventory(ProductEntity save,Integer amount,String token){
//        InventoryDto inventoryDto = new InventoryDto(save.getId(),amount);
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        token=token.substring(7);
//        httpHeaders.setBearerAuth(token);
//        HttpEntity<InventoryDto> entity=new HttpEntity<>(inventoryDto,httpHeaders);
//        ResponseEntity<String> exchange = restTemplate.exchange(URI.create(inventoryServiceUrl + "/add"),
//                HttpMethod.POST, entity, String.class);
//        String body = exchange.getBody();
//    }
//    public void deleteInventory(UUID productId,String token){
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        token=token.substring(7);
//        httpHeaders.setBearerAuth(token);
//        HttpEntity<UUID> entity=new HttpEntity<>(productId,httpHeaders);
//        ResponseEntity<String> exchange = restTemplate.exchange(URI.create(inventoryServiceUrl + "/delete"),
//                HttpMethod.DELETE, entity, String.class);
//        String body = exchange.getBody();
//
//    }

}
