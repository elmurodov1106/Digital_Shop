package com.example.digital_shop.service.tv;



import com.example.digital_shop.domain.dto.TvDto;
import com.example.digital_shop.entity.product.TvEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TvService {
    TvEntity add(TvDto tvDto, UUID userId, Integer amount, MultipartFile image) throws IOException;
    List<TvEntity> getAllTv(int size, int page);
    List<TvEntity> search(int page,int size,String name);
    Boolean deleteById(UUID id,UUID userId);
    TvEntity update(TvDto update, UUID tvId, UUID userId, Integer amount, MultipartFile image) throws IOException;

    List<TvEntity> getSellerTv(UUID sellerId);

}
