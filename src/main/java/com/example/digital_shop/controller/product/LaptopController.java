package com.example.digital_shop.controller.product;


import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.LaptopDto;
import com.example.digital_shop.entity.product.LaptopEntity;
import com.example.digital_shop.repository.laptop.LaptopRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import com.example.digital_shop.service.laptop.LaptopService;
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
@RequestMapping("/laptop")
@RequiredArgsConstructor
public class LaptopController {

    private final LaptopService laptopService;
    private final LaptopRepository laptopRepository;

    @GetMapping("/add")
    public String addGet() {
        return "LaptopAdd";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute LaptopDto laptopDto,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            HttpServletRequest request
    ) throws IOException {
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        laptopService.add(laptopDto,userId,amount,image);
//        model.addAttribute("message","Laptop successfully added");
        return "SellerMenu";
    }
    @GetMapping("/get-all")
    public String getAll(
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "0")  int page,
            Model model
    ){
      List<LaptopEntity> allLaptop = laptopService.getAllLaptops(size, page);
      if (allLaptop.isEmpty()){
          model.addAttribute("message","Laptop not found");
          return "redirect:/auth/seller/menu";
      }
      model.addAttribute("laptops",allLaptop);;
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

    @PostMapping("/update")
    public String update(
            @RequestBody LaptopDto laptopDto,
            @RequestParam UUID laptopId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model,
            HttpServletRequest request
    )throws IOException{
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        LaptopEntity update = laptopService.update(laptopDto,laptopId,userId, amount,image);
        if (update==null){
            model.addAttribute("message","laptop not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Laptop successfully");
        return "SellerMenu";
    }


    @PostMapping("/delete")
    public String delete(
            @RequestParam UUID laptopId,
            Model model,
            HttpServletRequest request
    ){
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        Boolean aBoolean = laptopService.deleteById(laptopId,userId);
        if (aBoolean==null){
            model.addAttribute("message","Laptop not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Laptop successfully deleted");
        return "SellerMenu";
    }

}
