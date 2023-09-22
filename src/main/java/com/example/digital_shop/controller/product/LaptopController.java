package com.example.digital_shop.controller.product;


import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.service.laptop.LaptopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product/laptop")
@RequiredArgsConstructor
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping("/add")
    public String addGet(
            @RequestParam UUID userId, Model model) {
        model.addAttribute("userId",userId);
        return "LaptopAdd";
    }



    @PostMapping("/add")
    public String add(
            @RequestBody LaptopDto laptopDto,
            @PathVariable UUID userId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    ) throws IOException {
        laptopService.add(laptopDto,userId,amount,image);
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }
    @GetMapping("/get-all")
    public String getAll(
            @RequestParam(defaultValue = "10",required = false)  int size,
            @RequestParam(defaultValue = "0",required = false)  int page,
            Model model
    ){
      List<LaptopEntity> allLaptop = laptopService.getAllLaptops(size, page);
      if (allLaptop.isEmpty()){
          model.addAttribute("message","Laptop not found");
          return "index";
      }
      model.addAttribute("laptop",allLaptop);;
      return "allLaptop";
    }

    @GetMapping("/search-by-name")
    public String search(
            @RequestParam(defaultValue = "10",required = false)  int size,
            @RequestParam(defaultValue = "0",required = false)  int page,
            @RequestParam String name,
            Model model
    ){
      List<LaptopEntity> search = laptopService.search(size,page,name);
      if (search.isEmpty()){
          model.addAttribute("message","Laptop not found");
          return "index";
      }
      model.addAttribute("laptop",search);
      return "search";
    }

    @PutMapping("/{userId}/update")
    @PreAuthorize(value = "hasRole('Seller')")
    public String update(
            @Valid  @RequestBody LaptopDto laptopDto,
            @PathVariable UUID userId,
            @RequestParam UUID laptopId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    )throws IOException{
        LaptopEntity update = laptopService.update(laptopDto,laptopId,userId, amount,image);
        if (update==null){
            model.addAttribute("message","laptop not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("userId",userId);
        model.addAttribute("message","Laptop successfully");
        return "SellerMenu";
    }


    @DeleteMapping("/{userId}/delete")
    public String delete(
            @PathVariable UUID userId,
            @RequestParam UUID laptopId,
            Model model
    ){
        Boolean aBoolean = laptopService.deleteById(laptopId,userId);
        if (aBoolean==null){
            model.addAttribute("message","Laptop not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("message","Laptop successfully deleted");
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }

}
