package com.example.digital_shop.service.laptop;


import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.laptop.LaptopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class LaptopServiceImpl implements LaptopService{
    private final LaptopRepository laptopRepository;
    private final ModelMapper modelMapper;

    @Override
    public LaptopEntity add(LaptopDto laptop, UUID userId, Integer amount, String  token) {
        LaptopEntity laptopEntity = modelMapper.map(laptop, LaptopEntity.class);
        laptopEntity.setUserId(userId);
        LaptopEntity saved = laptopRepository.save(laptopEntity);
//        addInventory(saved,amount,token);
        return saved;
    }

//    public void addInventory(LaptopEntity save, Integer amount, String token){
//        InventoryDto inventoryDto = new InventoryDto(save.getId(),amount);
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        token=token.substring(7);
//        httpHeaders.setBearerAuth(token);
//        HttpEntity<InventoryDto> entity=new HttpEntity<>(inventoryDto,httpHeaders);
//        ResponseEntity<String> exchange = restTemplate.exchange(URI.create(inventoryServiceUrl + "/add"),
//                HttpMethod.POST, entity, String.class);
//        String body = exchange.getBody();
//    }


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
    public Boolean deleteById(UUID laptopId, UUID userId,String token) {
        LaptopEntity laptopNotFound = laptopRepository.findById(laptopId)
                .orElseThrow(() -> new DataNotFoundException("Laptop not found"));
        if (laptopNotFound.getUserId().equals(userId)) {
            laptopRepository.deleteById(laptopId);
//            deleteInventory(laptopId,token);
            return true;
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    public LaptopEntity update(LaptopDto update, UUID laptopId, UUID userId) {
        LaptopEntity laptopEntity = laptopRepository.findById(laptopId)
                .orElseThrow(() -> new DataNotFoundException("Laptop not found"));
        if (laptopEntity.getUserId().equals(userId)) {
            modelMapper.map(update, laptopEntity);
            return laptopRepository.save(laptopEntity);
        }
        throw new DataNotFoundException("User not found");
    }

//    public void deleteInventory(UUID laptopId,String token){
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        token=token.substring(7);
//        httpHeaders.setBearerAuth(token);
//        HttpEntity<UUID> entity=new HttpEntity<>(laptopId,httpHeaders);
//        ResponseEntity<String> exchange = restTemplate.exchange(URI.create(inventoryServiceUrl +"/"+ laptopId + "/delete"),
//                HttpMethod.DELETE, entity, String.class);
//        String body = exchange.getBody();
//
//    }
}
