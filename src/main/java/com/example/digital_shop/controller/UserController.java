package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.UserCreatDto;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model){
        UUID userId = checkCookie(request);
        if (userId == null){
            return "index";
        }
        UserEntity byId = userService.getById(userId);
        model.addAttribute("user",byId);
        return "index";
    }
    @GetMapping("/menu")
    public String menu(HttpServletRequest request, Model model){
        UUID userId = checkCookie(request);
        if (userId == null){
            return "index";
        }
        UserEntity byId = userService.getById(userId);
        model.addAttribute("user",byId);
        if(byId.getRole().getName().equals("Seller")){
            return "SellerMenu";
        }
        return "index";
    }
    @GetMapping("/update")
    public String updateGet(
            HttpServletRequest request,
            Model model){
        UUID userId = checkCookie(request);
        if (userId == null){
            return "index";
        }
        UserEntity user = userService.getById(userId);
        model.addAttribute("user",user);
        return "UserUpdate";
    }
    @PostMapping("/update")
    public String update(
            @RequestParam String name,
            HttpServletRequest request,
            Model model){
        UUID userId = checkCookie(request);
        if (userId == null){
            return "index";
        }
        UserEntity userEntity = userService.updateUser(name, userId);
        model.addAttribute("user",userEntity);
        return "profile";
    }
    @GetMapping("/get-profile")
    public String getUserProfile(
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        if (userId == null){
            return "index";
        }
        UserEntity user = userService.getById(userId);
        model.addAttribute("user",user);
        return "profile";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(!userId.equals("null")){
            return UUID.fromString(userId);
        }
        return null;
    }

}
