package com.example.digital_shop.repository.tv;

import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.product.TvEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TvRepository extends JpaRepository<TvEntity, UUID> {
    List<TvEntity> searchTvEntitiesByModelContainingIgnoreCase(String model, Pageable pageable);

    TvEntity findTvEntityById(UUID tvId);

    List<TvEntity> findTvEntitiesByUserIdEquals(UUID userId);

}
