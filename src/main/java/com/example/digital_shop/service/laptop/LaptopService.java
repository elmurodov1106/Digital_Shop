package com.example.digital_shop.service.laptop;



import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.domain.dto.LaptopUpdateDto;
import com.example.digital_shop.entity.product.LaptopEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface LaptopService {
    LaptopEntity add(LaptopDto laptop, UUID userId, Integer amount, MultipartFile image) throws IOException;
    List<LaptopEntity> getAllLaptops(int size, int page);
    List<LaptopEntity> search(int page,int size,String name);
    Boolean deleteById(UUID laptopId, UUID userId);
    LaptopEntity update(LaptopUpdateDto update, UUID id, UUID userId, Integer amount, MultipartFile image) throws IOException;
    List<LaptopEntity> getSellerLaptop(int page,int size,UUID sellerId);
    List<LaptopEntity> findLaptopEntityByOwnerId(UUID userId);
}
