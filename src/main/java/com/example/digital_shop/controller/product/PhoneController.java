package com.example.digital_shop.controller.product;


import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.entity.product.PhoneEntity;
import com.example.digital_shop.service.phone.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("/add")
    public String addGet(
            @RequestParam UUID userId, Model model) {
        model.addAttribute("userId",userId);
        return "PhoneAdd";
    }

    @PostMapping("{userId}/add")
    public String add(
            @ModelAttribute PhoneDto phoneDto,
            @PathVariable UUID userId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    )throws IOException {
        phoneService.add(phoneDto,userId,amount,image);
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam int size,
            @RequestParam int page,
            Model model) {
        List<PhoneEntity> allPhone = phoneService.getAllPhone(size, page);
        if (allPhone.isEmpty()){
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

    @PutMapping("/{userId}/update")
    public String update(
            @RequestBody PhoneDto phoneDto,
            @PathVariable UUID userId,
            @RequestParam UUID phoneId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    )  throws IOException {
        PhoneEntity update = phoneService.update(phoneDto, phoneId,amount, userId,image);
        if(update==null){
            model.addAttribute("message","Phone not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("userId",userId);
        model.addAttribute("message","Product successfully updated");
        return "SellerMenu";
    }



    @DeleteMapping("/{userId}/delete")
    public String delete(
            @PathVariable UUID userId,
            @RequestParam UUID phoneId,
            Model model
    ) {
        Boolean aphone = phoneService.deleteById(phoneId, userId);
        if (aphone==null){
            model.addAttribute("message","Phone not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("message","Phone successfully deleted");
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }









}
