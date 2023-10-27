package com.example.digital_shop.service.laptop;

import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.domain.dto.LaptopUpdateDto;
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
        return laptopRepository.save(laptopEntity);

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
    public LaptopEntity update(LaptopUpdateDto update, UUID laptopId, UUID userId, Integer amount, MultipartFile image) throws IOException {
        LaptopEntity laptopEntity = laptopRepository.findLaptopEntityById(laptopId);
        if (laptopEntity == null) {
            return null;
        }
        if (laptopEntity.getUserId().equals(userId)) {
            if(!update.getName().equals("")){
                laptopEntity.setName(update.getName());
            }
            if(!update.getModel().equals("")){
                laptopEntity.setModel(update.getModel());
            }
            if(update.getCost()!=null){
                laptopEntity.setCost(update.getCost());
            }
            if(update.getAmount()!=null&& update.getAmount()>=1){
                laptopEntity.setAmount(update.getAmount());
            }
            if(!update.getProductType().equals("")){
               laptopEntity.setProductType(update.getProductType());
            }
            String s = Base64.getEncoder().encodeToString(image.getBytes());
            if (!s.equals(" ") && !s.equals("")) {
               laptopEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            if(update.getBattery()!=null){
                laptopEntity.setBattery(update.getBattery());
            }
            if(!update.getColour().equals("")){
                laptopEntity.setColour(update.getColour());
            }
            if(update.getGhz()!=null){
                laptopEntity.setGhz(update.getGhz());
            }
            if(update.getMemory()!=null){
                laptopEntity.setMemory(update.getMemory());
            }
            if(update.getScreenSize()!=null){
                laptopEntity.setScreenSize(update.getScreenSize());
            }
            if(update.getRam()!=null){
                laptopEntity.setRam(update.getRam());
            }
            if(update.getWeight()!=null){
                laptopEntity.setWeight(update.getWeight());
            }
            laptopEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            return laptopRepository.save(laptopEntity);
        }
        return null;
    }

    @Override
    public List<LaptopEntity> getSellerLaptop(UUID sellerId) {
        List<LaptopEntity> laptopEntityById = laptopRepository.findLaptopEntitiesByUserIdEquals(sellerId);
        if(laptopEntityById.isEmpty()){
            return null;
        }
        return laptopEntityById;
    }


}
