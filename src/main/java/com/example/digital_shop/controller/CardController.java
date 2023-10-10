package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.service.payment.CardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/add")
    public String add(
            @Valid @RequestBody CardCreatedDto cardCreatedDto,
            HttpServletRequest request
    ){
        UUID userId = UUID.fromString(CookieValue.getValue("userId",request));
        cardService.add(cardCreatedDto,userId);
        return "";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam int size,
            @RequestParam int page
    ){
        cardService.getAllUserCards(size, page);
        return "";
    }

    @PutMapping("/update")
    public String update(
            @RequestBody CardCreatedDto cardCreatedDto,
            @RequestParam UUID cardId,
           HttpServletRequest request
        ){
        UUID userId = UUID.fromString(CookieValue.getValue("userId",request));
        cardService.update(cardCreatedDto,cardId,userId);
        return "";
    }

    @DeleteMapping("/delete")
    public String delete(
            @RequestParam UUID cardId,
            HttpServletRequest request
    ){
        UUID userId = UUID.fromString(CookieValue.getValue("userId",request));
        cardService.deleteById(userId, cardId);
        return "";
    }

}
