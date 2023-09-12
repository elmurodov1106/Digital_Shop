package com.example.digital_shop.repository.phone;

import com.example.digital_shop.entity.product.PhoneEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, UUID> {
//    List<PhoneEntity> searchPhoneEntitiesByModelContainingIgnoreCase(String model, Pageable pageable);

}

