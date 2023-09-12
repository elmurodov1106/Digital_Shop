package com.example.digital_shop.service.phone;



import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.entity.product.PhoneEntity;

import java.util.List;
import java.util.UUID;

public interface PhoneService {
    PhoneEntity add(PhoneDto phoneDto, UUID userId, Integer amount, String token);

    List<PhoneEntity> getAllPhone(int size, int page);
    List<PhoneEntity> search(int page,int size,String name);
    Boolean deleteById(UUID id,UUID userId,String token);
    PhoneEntity update(PhoneDto update,UUID id,UUID userId);


}
