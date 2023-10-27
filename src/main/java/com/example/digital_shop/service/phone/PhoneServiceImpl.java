package com.example.digital_shop.service.phone;

import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.domain.dto.PhoneUpdateDto;
import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.entity.product.PhoneEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.phone.PhoneRepository;
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
public class PhoneServiceImpl implements PhoneService{

    private final PhoneRepository phoneRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;



    @Override
    @Transactional
    public PhoneEntity add(PhoneDto phoneDto, UUID userId, Integer amount, MultipartFile image) throws IOException {
        PhoneEntity phoneEntity = modelMapper.map(phoneDto,PhoneEntity.class);
        phoneEntity.setUserId(userId);
        phoneEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        return phoneRepository.save(phoneEntity);
    }
    @Override
    public List<PhoneEntity> getAllPhone(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<PhoneEntity> content = phoneRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            return null;
        }
        return content;
    }

    @Override
    public List<PhoneEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return phoneRepository.searchPhoneEntitiesByModelContainingIgnoreCase(model, pageable);
    }



    @Override
    @Transactional
    public Boolean deleteById(UUID phoneId, UUID userId) {
        PhoneEntity phoneNotFound = phoneRepository.findPhoneEntityById(phoneId);
        if (phoneNotFound == null) {
            return null;
        }
        if (phoneNotFound.getUserId().equals(userId)){
            inventoryRepository.deleteByProductIdEquals(phoneId);
            phoneRepository.deleteById(phoneId);
            return true;
        }
        return null;
    }
    @Override
    @Transactional
    public PhoneEntity update(PhoneUpdateDto update, UUID phoneId, UUID userId, MultipartFile image) throws IOException {
        PhoneEntity phoneEntity = phoneRepository.findPhoneEntityById(phoneId);
        if (phoneEntity==null){
            return null;
        }
        if (phoneEntity.getUserId().equals(userId)){
            if(!update.getName().equals("")){
                phoneEntity.setName(update.getName());
            }
            if(!update.getModel().equals("")){
                phoneEntity.setModel(update.getModel());
            }
            if(update.getCost()!=null){
                phoneEntity.setCost(update.getCost());
            }
            if(update.getAmount()!=null&& update.getAmount()>=1){
                phoneEntity.setAmount(update.getAmount());
            }
            String s = Base64.getEncoder().encodeToString(image.getBytes());
            if (!s.equals(" ") && !s.equals("")) {
                phoneEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            if(update.getBattery()!=null){
                phoneEntity.setBattery(update.getBattery());
            }
            if(!update.getColour().equals("")){
                phoneEntity.setColour(update.getColour());
            }
            if(update.getMemory()!=null){
                phoneEntity.setMemory(update.getMemory());
            }
            if(update.getRam()!=null){
                phoneEntity.setRam(update.getRam());
            }
            if(update.getWeight()!=null){
                phoneEntity.setWeight(update.getWeight());
            }
            if(update.getBackCamera()!=null){
                phoneEntity.setBackCamera(update.getBackCamera());
            }
            if(update.getFrontCamera()!=null){
                phoneEntity.setFrontCamera(update.getFrontCamera());
            }
            if(!update.getSize().equals("")){
                phoneEntity.setSize(update.getSize());
            }
            return phoneRepository.save(phoneEntity);
        }
        return null;
    }

    public List<PhoneEntity> getSellerPhone(int page,int size,UUID sellerId) {
        Pageable pageable = PageRequest.of(page, size);
        List<PhoneEntity> phoneEntitiesByUserIdEquals = phoneRepository.findPhoneEntitiesByUserIdEquals(pageable,sellerId);
        return phoneEntitiesByUserIdEquals;
    }


}
