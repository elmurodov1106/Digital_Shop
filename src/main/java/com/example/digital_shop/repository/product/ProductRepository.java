package com.example.digital_shop.repository.product;


import com.example.digital_shop.entity.product.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> searchProductEntitiesByModelContainingIgnoreCase(String model, Pageable pageable);
    List<ProductEntity> findProductEntitiesByUserIdAndProductTypeEqualsIgnoreCase(UUID userId, String type,Pageable pageable);
    ProductEntity findProductEntityById(UUID productId);
    List<ProductEntity> findProductEntityByUserIdEquals(UUID userId);
}
