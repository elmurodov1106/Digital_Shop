package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.UserCreatDto;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/menu")
    public String menu(HttpServletRequest request, Model model){
        UUID userId = UUID.fromString(CookieValue.getValue("userId", request));
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
        UUID userId= UUID.fromString(CookieValue.getValue("userId",request));
        UserEntity user = userService.getById(userId);
        model.addAttribute("user",user);
        return "profile";
    }
    @PostMapping("/update")
    public ResponseEntity<UserEntity> update(
            @RequestBody UserCreatDto userCreatDto,
            HttpServletRequest request){
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        return ResponseEntity.ok(userService.updateUser(userCreatDto,userId));
    }
    @GetMapping("/get-profile")
    public String getUserProfile(
            HttpServletRequest request,
            Model model
    ){
        UUID userId =UUID.fromString(CookieValue.getValue("userId",request));
        UserEntity user = userService.getById(userId);
        model.addAttribute("user",user);
        return "profile";
    }

}
