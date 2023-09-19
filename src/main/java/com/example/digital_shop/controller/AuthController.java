package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.*;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/index")
    public String yourPage() {
        return "index";
    }


    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserCreatDto userCreatDto,
                         Model model) {
        UserEntity save = userService.save(userCreatDto);
        if(save==null){
            model.addAttribute("message","Email already exists");
            return "signUp";
        }
        model.addAttribute("user",save);
      return "verify";
    }
    @GetMapping("/sign-up")
    public String signUpGet(){
        return "signUp";
    }

    @PostMapping("/verify")
    public String verify(@RequestParam UUID userId,
                         @RequestParam String sendingCode,
                         Model model) {
        Boolean isActive=userService.verify(sendingCode,userId);
         if(isActive){
             model.addAttribute("isActive",true);
             return "signIn";
         }
         model.addAttribute("message","Activation code is incorrect or ragged");
         return "verify";
    }

    @GetMapping("/new-code")
    public String NewVerifyCode() {
         return "newCode";
    }
    @PostMapping("/new-code")
    public String getNewVerifyCode(@RequestParam String email){
        userService.getNewVerifyCode(email);
        return "verify";
    }

    @GetMapping("/sign-in")
    public String signInGet() {
        return "signIn";
    }
    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute LoginDto loginDto,Model model){
        UserEntity user = userService.signIn(loginDto);
        if(user==null){
            model.addAttribute("message","Username or password is wrong!!! Please try again");
            return "signIn";
        }
        model.addAttribute("user",user);
        return "index";
    }
   @GetMapping("/seller/sign-up")
   public String sellerSignUpGet(){
        return "SellerSignUp";
   }
    @PostMapping("/seller/sign-up")
    public String sellerSignUp(@ModelAttribute SellerDto sellerDto,Model model) {
        UserEntity user = userService.saveSeller(sellerDto);
        if(user==null){
            model.addAttribute("message","Email already exists");
            return "SellerSignUp";
        }
        model.addAttribute("user",user);
        return "verify";
    }
}
