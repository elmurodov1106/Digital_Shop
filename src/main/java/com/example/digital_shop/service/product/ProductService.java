package com.example.digital_shop.service.product;


import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.product.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductEntity add(ProductCreatDto product, UUID userId, Integer amount);
    List<ProductEntity> getAllProducts(int size, int page);
    List<ProductEntity> search(int page,int size,String name);
    Boolean deleteById(UUID id,UUID userId,String token);
    ProductEntity update(ProductCreatDto update,UUID productId,Integer amount,UUID userId);
}
