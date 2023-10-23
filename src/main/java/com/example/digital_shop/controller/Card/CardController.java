package com.example.digital_shop.controller.Card;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.payment.CardService;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @GetMapping("/add")
    public String addPage(){
        return "addCard";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute
            CardCreatedDto cardCreatedDto,
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        if(userId == null){
            return "index";
        }
        UserEntity user = userService.getById(userId);
        cardService.add(cardCreatedDto,userId);
        model.addAttribute("user",user);
        if (user.getRole().getName().equals("Customer")){
            return "index";
        }
        model.addAttribute("message","Card successfully added");
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam int size,
            @RequestParam int page
    ){
        cardService.getAllUserCards(size, page);
        return "";
    }

    @GetMapping("/update")
    public String updatePage(){
        return "updateCard";
    }
    @PostMapping("/update")
    public String update(
            @RequestBody CardCreatedDto cardCreatedDto,
            @RequestParam UUID cardId,
           HttpServletRequest request,
            Model model
        ){
        UUID userId = checkCookie(request);
        if(userId == null){
            return "index";
        }
        UserEntity user = userService.getById(userId);
        cardService.update(cardCreatedDto,cardId,userId);
        if (user.getRole().getName().equals("Customer")){
            return "index";
        }
        model.addAttribute("message","Card successfully updated");
        return "SellerMenu";
    }

    @DeleteMapping("/delete")
    public String delete(
            @RequestParam UUID cardId,
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        if(userId == null){
            return "index";
        }
        UserEntity user = userService.getById(userId);
        cardService.deleteById(userId, cardId);
        if (user.getRole().getName().equals("Customer")){
            return "index";
        }
        model.addAttribute("message","Card successfully added");
        return "SellerMenu";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(!userId.equals("null")){
            return UUID.fromString(userId);
        }
        return null;
    }

}
