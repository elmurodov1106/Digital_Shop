package com.example.digital_shop.service.product;


import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.product.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductEntity add(ProductCreatDto product, UUID userId, Integer amount, MultipartFile image) throws IOException;
    List<ProductEntity> getAllProducts(int size, int page);
    List<ProductEntity> search(int page,int size,String name);
    Boolean deleteById(UUID id,UUID userId);
    ProductEntity update(ProductCreatDto update,UUID productId,Integer amount,UUID userId,MultipartFile image) throws IOException;
}
