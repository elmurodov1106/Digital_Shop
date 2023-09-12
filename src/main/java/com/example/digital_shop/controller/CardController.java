package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.payment.CardEntity;
import com.example.digital_shop.exception.RequestValidationException;
import com.example.digital_shop.service.payment.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/{ownerId}/add")
    public ResponseEntity<CardEntity> add(
           @Valid @RequestBody CardCreatedDto cardCreatedDto,
            @PathVariable UUID ownerId,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return ResponseEntity.ok(cardService.add(cardCreatedDto,ownerId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CardEntity>> getAll(
            @RequestParam int size,
            @RequestParam int page
    ){
        return ResponseEntity.status(200).body(cardService.getAllUserCards(size, page));
    }

    @PutMapping("/{ownerId}/update")
    public ResponseEntity<CardEntity> update(
          @Valid  @RequestBody CardCreatedDto cardCreatedDto,
            @PathVariable UUID ownerId,
            @RequestParam UUID card_id,
            BindingResult bindingResult
        ){
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return ResponseEntity.ok(cardService.update(cardCreatedDto,ownerId,card_id));
    }

    @DeleteMapping("/{ownerId}/delete")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID ownerId,
            @RequestParam UUID card_id
    ){
        return ResponseEntity.ok(cardService.deleteById(ownerId, card_id));
    }

}
