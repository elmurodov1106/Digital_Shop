package com.example.digital_shop.service.tv;



import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.domain.dto.TvDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.PhoneEntity;
import com.example.digital_shop.entity.product.TvEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.tv.TvRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service

public class TvServiceImpl implements TvService {

    private final TvRepository tvRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional
    public TvEntity add(TvDto tvDto, UUID userId, Integer amount, MultipartFile image) throws IOException {
        TvEntity tvEntity = modelMapper.map(tvDto,TvEntity.class);
        tvEntity.setUserId(userId);
        tvEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        TvEntity save = tvRepository.save(tvEntity);
        InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
        inventoryCreateDto.setProductId(save.getId());
        inventoryCreateDto.setProductCount(amount);
        InventoryEntity inventoryEntity = modelMapper.map(inventoryCreateDto, InventoryEntity.class);
        inventoryRepository.save(inventoryEntity);
        return save;
    }


    @Override
    public List<TvEntity> getAllTv(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<TvEntity> content = tvRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Tv not found");
        }
        return content;
    }

    @Override
    public List<TvEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return tvRepository.searchTvEntitiesByModelContainingIgnoreCase(model, pageable);
    }



    @Override
    @Transactional
    public Boolean deleteById(UUID tvId, UUID userId) {
        TvEntity tvNotFound = tvRepository.findTvEntityById(tvId);
        if (tvNotFound == null) {
            return null;
        }
        if (tvNotFound.getUserId().equals(userId)){
            inventoryRepository.deleteByProductIdEquals(tvId);
            tvRepository.deleteById(tvId);
            return true;
        }
        return null;
    }





    @Override
    @Transactional
    public TvEntity update(TvDto tvDto, UUID tvId,UUID userId,Integer amount,MultipartFile image) throws IOException {
        TvEntity tvEntity = tvRepository.findTvEntityById(tvId);
        if (tvEntity==null){
            return null;
        }
        InventoryEntity inventoryEntity = inventoryRepository.getByProductId(tvId);
        if (tvEntity
                .getUserId().equals(userId)){
            modelMapper.map(tvDto,tvEntity
            );
            inventoryEntity.setProductCount(amount);
            inventoryRepository.save(inventoryEntity);
            tvEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            return tvRepository.save(tvEntity
            );
        }
        return null;
    }
}

