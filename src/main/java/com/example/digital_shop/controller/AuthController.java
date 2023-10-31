package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.*;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.product.ProductService;
import com.example.digital_shop.service.seller.SellerService;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final ProductService productService;
    private final SellerService sellerService;

    @GetMapping("/index")
    public String yourPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            HttpServletRequest request
    ) {
        List<ProductEntity> allProducts = productService.getAll();
        UUID userId= checkCookie(request);
        if(userId!= null){
         model.addAttribute("user",userService.getById(userId));
        }
        model.addAttribute("products", allProducts);
        return "index";
    }

    @GetMapping("/about")
    public String about(HttpServletRequest request, Model model) {
        UUID userId = checkCookie(request);
        if(userId!=null){
            model.addAttribute("user",userService.getById(userId));
        }
        return "About";
    }
    @GetMapping("/seller/menu")
    public String seller(
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            UUID userId = userService.getIdByEmail(email);
            model.addAttribute("userIdForSeller", userId);
        }

        return "SellerMenu";
    }


    @GetMapping("/contact")
    public String contact(HttpServletRequest request, Model model) {
//        UUID userId=checkCookie(request);
//        UserEntity user = userService.getById(userId);
//        if (user == null) {
//            return "contactUs";
//        }
//        model.addAttribute("user",user);
        return "contactUs";
    }
    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserCreatDto userCreatDto,
                         Model model) {
        if (!userCreatDto.getEmail().endsWith("@gmail.com")){
            model.addAttribute("message","Email did not  match");
            return "signUp";
        }
        UUID save = userService.save(userCreatDto);
        if (save == null) {
            model.addAttribute("message", "Email already exists");
            return "signUp";
        }
        model.addAttribute("id", save);
        return "verify";
    }

    @GetMapping("/sign-up")
    public String signUpGet() {
        return "signUp";
    }

//    @GetMapping("/verify")
//    public String verifyGet() {
//        return "verify";
//    }

    @GetMapping("/verify")
    public String verify(@RequestParam UUID id,
                         @RequestParam String sendingCode,
                         Model model) {
        Boolean isActive = userService.verify(sendingCode, id);
        if (isActive==null) {
            model.addAttribute("message", "Activation code is incorrect or ragged");
            model.addAttribute("id",id);
            return "verify";
        }
        model.addAttribute("isActive", true);
        return "signIn";
    }

    @GetMapping("/new-code")
    public String NewVerifyCode() {
        return "newCode";
    }

    @PostMapping("/new-code")
    public String getNewVerifyCode(@RequestParam String email, Model model) {
        UserEntity user = userService.getNewVerifyCode(email);
        model.addAttribute("user", user);
        return "verify";
    }

    @GetMapping("/sign-in")
    public String signInGet() {
        return "signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute LoginDto loginDto, Model model, HttpServletResponse response) {
        UserEntity user = userService.signIn(loginDto);
        if (user == null) {
            model.addAttribute("message", "Username or password is wrong!!! Please try again");
            return "signIn";
        }
        Cookie cookie=new Cookie("userId",user.getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        if (user.getRole().getName().equals("Seller")) {
            model.addAttribute("user", user);

            return "SellerMenu";
        }
        model.addAttribute("products",productService.getAllProducts(10,0));
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/menu")
    public String menu( Model model,  HttpServletRequest request){
        UUID userId = checkCookie(request);
        UserEntity user= userService.getById(userId);
        List<ProductEntity> allProducts = productService.getAll();
        model.addAttribute("user",user);
        if(user.getRole().getName().equals("Seller")){
            return "SellerMenu";
        }
        model.addAttribute("products", allProducts);
        return "redirect:/auth/index";
    }

    @GetMapping("/seller/sign-up")
    public String sellerSignUpGet() {
        return "SellerSignUp";
    }

    @PostMapping("/seller/sign-up")
    public String sellerSignUp(@ModelAttribute SellerDto sellerDto, Model model) {
        UserEntity user = userService.saveSeller(sellerDto);
        if (user == null) {
            model.addAttribute("message", "This email or passport or phone number already exists!!! Please sign in");
            return "SellerSignUp";
        }
        model.addAttribute("user", user);
        return "verify";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie=new Cookie("userId","");
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/auth/index";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!=null){
            return UUID.fromString(userId);
        }
        return null;
    }
}
