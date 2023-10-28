package com.example.digital_shop.service.phone;



import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.domain.dto.PhoneUpdateDto;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.entity.product.PhoneEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PhoneService {
    PhoneEntity add(PhoneDto phoneDto, UUID userId, Integer amount, MultipartFile image) throws IOException;
   PhoneEntity getById(UUID phoneId);
    List<PhoneEntity> getAllPhone(int size, int page);
    List<PhoneEntity> search(int page,int size,String name);
    Boolean deleteById(UUID id,UUID userId);
    PhoneEntity update(PhoneUpdateDto update, UUID id, UUID userId, MultipartFile image) throws IOException;

    List<PhoneEntity> getSellerPhone(int page,int size,UUID sellerId);


}
