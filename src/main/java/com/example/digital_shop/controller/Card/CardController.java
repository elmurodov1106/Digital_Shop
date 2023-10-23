package com.example.digital_shop.controller.Card;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.CardCreatedDto;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.repository.RoleRepository;
import com.example.digital_shop.service.card.CardService;
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
    private final RoleRepository roleRepository;

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
        UserEntity byId = userService.getById(userId);
        cardService.add(cardCreatedDto,userId);
        model.addAttribute("user",byId);
        model.addAttribute("message","Card successfully added");
        return "index";
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
            @RequestParam String name,
            @RequestParam UUID cardId,
           HttpServletRequest request
        ){
        UUID userId = checkCookie(request);
        cardService.update(name,cardId,userId);
        return "";
    }

    @DeleteMapping("/delete")
    public String delete(
            @RequestParam UUID cardId,
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        UserEntity user= userService.getById(userId);
        Boolean aBoolean = cardService.deleteById(userId, cardId);
        model.addAttribute("user",user);
        if(user.getRole().getName().equals("Seller")){
            if(aBoolean){
                model.addAttribute("message","Successfully deleted");
            }
            model.addAttribute("message","Card not found");
            return "SellerMenu";
        }
        if(aBoolean){
            model.addAttribute("message","Successfully deleted");
            return "index";
        }
        return "index";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!=null){
            return UUID.fromString(userId);
        }
        return null;
    }

}
