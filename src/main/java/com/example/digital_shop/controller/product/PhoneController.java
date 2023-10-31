package com.example.digital_shop.controller.product;


import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.domain.dto.PhoneUpdateDto;
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
        if(userId!=null) {
            UserEntity byId = userService.getById(userId);
            model.addAttribute("user", byId);
        }
        if (allPhone == null){
            model.addAttribute("message","Phone not found");
            return "allPhones";
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
    @GetMapping("/update")
    public String updateGet(@RequestParam UUID phoneId,Model model){
        PhoneEntity byId = phoneService.getById(phoneId);
        if(byId==null){
            model.addAttribute("message","Phone not found");
            return "allPhones";
        }
        model.addAttribute("phone",byId);
        return "updatePhone";
    }
    @PostMapping("/update")
    public String update(
            @ModelAttribute PhoneUpdateDto phoneUpdateDto,
            @RequestParam UUID phoneId,
            @RequestParam MultipartFile image,
            Model model,
            HttpServletRequest request
    )  throws IOException {
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
        PhoneEntity update = phoneService.update(phoneUpdateDto, phoneId, userId,image);
        if(update==null){
            model.addAttribute("message","Phone not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Product successfully updated");
        return "SellerMenu";
    }
    @GetMapping("/delete")
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

    @GetMapping("/get-seller")
    public String getSellerPhone(
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "0")  int page,
            Model model,
            HttpServletRequest request
    ){
        UUID userId = checkCookie(request);
        List<PhoneEntity> allPhone = phoneService.getSellerPhone( page,size,userId);
        if(userId!= null){
            model.addAttribute("user",userService.getById(userId));
        }
        if (allPhone.isEmpty()){
            model.addAttribute("phones",allPhone);;
            model.addAttribute("message","Phone not found");
            return "sellerPhone";
        }
        model.addAttribute("phones",allPhone);;
        return "sellerPhone";
    }



    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!=null){
            return UUID.fromString(userId);
        }
        return null;
    }

}
