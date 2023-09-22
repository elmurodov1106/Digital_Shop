package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.exception.RequestValidationException;
import com.example.digital_shop.service.inventoryService.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/add")
    public InventoryEntity add(
            @Valid @RequestBody InventoryCreateDto inventoryCreateDto,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return inventoryService.add(inventoryCreateDto);
    }

    @PutMapping("/update")
    public InventoryEntity update(
            @Valid @RequestParam UUID productId,
            @RequestParam UUID inventoryId,
            @RequestBody InventoryCreateDto inventoryCreateDto,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return inventoryService.update(inventoryCreateDto,inventoryId,productId);
    }
    @DeleteMapping("/delete")
    public Boolean delete(
            @RequestParam UUID inventoryId
    ){
        inventoryService.deleteByInventoryId(inventoryId);
        return true;
    }
    @DeleteMapping("/delete")
    public Boolean deleteByProductId(
            @RequestParam UUID productId
    ){
        inventoryService.deleteByProductId(productId);
        return true;
    }
}
