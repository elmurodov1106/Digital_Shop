package com.example.digital_shop.controller.product;


import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.entity.product.PhoneEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.phone.PhoneService;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;
    private final UserService userService;
    @GetMapping("/add")
    public String addGet() {
        return "PhoneAdd";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute PhoneDto phoneDto,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            HttpServletRequest request
    )throws IOException {
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
        phoneService.add(phoneDto,userId,amount,image);
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            HttpServletRequest request) {
        List<PhoneEntity> allPhone = phoneService.getAllPhone(size, page);
        UUID userId = checkCookie(request);
        if(userId==null){
            return "index";
        }
        UserEntity byId = userService.getById(userId);
        model.addAttribute("user",byId);
        if (allPhone == null){
            model.addAttribute("message","Phone not found");
            return "index";
        }
        model.addAttribute("phone",allPhone);
        return "allPhones";
    }


    @GetMapping("/search")
    public String search(
            @RequestParam(defaultValue = "10",required = false) int size,
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam String name,
            Model model
    ){
      List<PhoneEntity> search = phoneService.search(size,page,name);
      if (search.isEmpty()){
          model.addAttribute("message","phone not found");
          return "index";
      }
      model.addAttribute("phone",search);
      return "search";
    }
    @PostMapping("/update")
    public String update(
            @RequestBody PhoneDto phoneDto,
            @RequestParam UUID phoneId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model,
            HttpServletRequest request
    )  throws IOException {
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
        PhoneEntity update = phoneService.update(phoneDto, phoneId,amount, userId,image);
        if(update==null){
            model.addAttribute("message","Phone not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Product successfully updated");
        return "SellerMenu";
    }
    @PostMapping("/delete")
    public String delete(
            @RequestParam UUID phoneId,
            Model model,
            HttpServletRequest request
    ) {
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
        Boolean aphone = phoneService.deleteById(phoneId, userId);
        if (aphone==null){
            model.addAttribute("message","Phone not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Phone successfully deleted");
        return "SellerMenu";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!=null){
            return UUID.fromString(userId);
        }
        return null;
    }

}
