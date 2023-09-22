package com.example.digital_shop.service.laptop;


import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.laptop.LaptopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class LaptopServiceImpl implements LaptopService{
    private final LaptopRepository laptopRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public LaptopEntity add(LaptopDto laptop, UUID userId, Integer amount, MultipartFile image) throws IOException {
        LaptopEntity laptopEntity = modelMapper.map(laptop, LaptopEntity.class);
        laptopEntity.setUserId(userId);
        laptopEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        LaptopEntity savedLaptopEntity = laptopRepository.save(laptopEntity);
        InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
        inventoryCreateDto.setProductId(savedLaptopEntity.getId());
        inventoryCreateDto.setProductCount(amount);
        InventoryEntity inventoryEntity = modelMapper.map(inventoryCreateDto, InventoryEntity.class);
        inventoryRepository.save(inventoryEntity);
        return savedLaptopEntity;

    }



    @Override
    public List<LaptopEntity> getAllLaptops(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<LaptopEntity> content = laptopRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Laptop not found");
        }
        return content;
    }

    @Override
    public List<LaptopEntity> search(int page, int size, String name) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return laptopRepository.searchLaptopEntitiesByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID laptopId, UUID userId) {
      LaptopEntity laptopEntity = laptopRepository.findLaptopEntityById(laptopId);
      if (laptopEntity==null){
          return null;
      }
      if (laptopEntity.getUserId().equals(userId)){
          inventoryRepository.deleteByProductIdEquals(laptopId);
          laptopRepository.deleteById(laptopId);
          return true;
      }
      return null;
    }

    @Override
    @Transactional
    public LaptopEntity update(LaptopDto update, UUID laptopId, UUID userId,Integer amount,MultipartFile image) throws IOException {
        LaptopEntity laptopEntity = laptopRepository.findLaptopEntityById(laptopId);
        if (laptopEntity == null) {
            return null;
        }
        InventoryEntity inventoryEntity = inventoryRepository.getByProductId(laptopId);
        if (laptopEntity.getUserId().equals(userId)) {
            modelMapper.map(update, laptopEntity);
            inventoryEntity.setProductCount(amount);
            inventoryRepository.save(inventoryEntity);
            laptopEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            return laptopRepository.save(laptopEntity);
        }
        return null;
    }


    }
