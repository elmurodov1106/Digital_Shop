package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.*;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.exception.RequestValidationException;
import com.example.digital_shop.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
      model.addAttribute(userService.save(userCreatDto));
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
    public ResponseEntity<JwtResponse> signIn(@Valid
                                                  @RequestBody LoginDto loginDto,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(userService.signIn(loginDto));
    }

    @PostMapping("/seller/sign-up")
    public ResponseEntity<UserEntity> sellerSignUp(
            @Valid @RequestBody SellerDto sellerDto,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(userService.saveSeller(sellerDto));
    }
}
