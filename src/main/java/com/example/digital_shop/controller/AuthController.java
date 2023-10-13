package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.*;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.product.ProductService;
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

    @GetMapping("/index")
    public String yourPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<ProductEntity> allProducts = productService.getAll();
        if (allProducts == null) {
            model.addAttribute("message", "Product not found");
            return "index";
        }
        model.addAttribute("products", allProducts);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
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
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        UserEntity byId = userService.getById(userId);
        model.addAttribute("user",byId);
        return "contactUs";
    }

    @GetMapping("/basket")
    public String basket() {
        return "basket";
    }


    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserCreatDto userCreatDto,
                         Model model) {
        UserEntity save = userService.save(userCreatDto);
        if (save == null) {
            model.addAttribute("message", "Email already exists");
            return "signUp";
        }
        model.addAttribute("user", save);
        return "verify";
    }

    @GetMapping("/sign-up")
    public String signUpGet() {
        return "signUp";
    }

    @GetMapping("/verify")
    public String verifyGet() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verify(@RequestParam UUID userId,
                         @RequestParam String sendingCode,
                         Model model) {
        Boolean isActive = userService.verify(sendingCode, userId);
        if (isActive) {
            model.addAttribute("isActive", true);
            return "signIn";
        }
        model.addAttribute("message", "Activation code is incorrect or ragged");
        return "verify";
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
            return "redirect:/auth/seller/menu";
        }
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/seller/sign-up")
    public String sellerSignUpGet() {
        return "SellerSignUp";
    }

    @PostMapping("/seller/sign-up")
    public String sellerSignUp(@ModelAttribute SellerDto sellerDto, Model model) {
        UserEntity user = userService.saveSeller(sellerDto);
        if (user == null) {
            model.addAttribute("message", "Email already exists");
            return "SellerSignUp";
        }
        model.addAttribute("user", user);
        return "verify";
    }
}
