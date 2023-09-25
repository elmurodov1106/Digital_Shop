package com.example.digital_shop.controller.product;

import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/add")
    public String addGet(
            @RequestParam UUID userId, Model model) {
      model.addAttribute("userId",userId);
      return "ProductAdd";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute ProductCreatDto productCreatDto,
            @RequestParam UUID userId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    ) throws IOException {
       productService.add(productCreatDto,userId,amount,image);
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam int page,
            @RequestParam int size,
            Model model) {
        List<ProductEntity> allProducts = productService.getAllProducts(page, size);
        if(allProducts.isEmpty()){
            model.addAttribute("message","Product not found");
            return "index";
        }
        model.addAttribute("products",allProducts);
        return "allProducts";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam String name,
            Model model
    ) {
        List<ProductEntity> search = productService.search(size, page, name);
        if(search.isEmpty()){
            model.addAttribute("message","Product not found");
            return "index";
        }
        model.addAttribute("products",search);
        return "search";
    }

    @PutMapping("/update")
    public String update(
            @RequestParam UUID userId,
            @RequestBody ProductCreatDto productCreatDto,
            @RequestParam UUID productId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            Model model
    ) throws IOException {
        ProductEntity update = productService.update(productCreatDto, productId, amount, userId,image);
        if(update==null){
            model.addAttribute("message","Product not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("userId",userId);
        model.addAttribute("message","Product successfully updated");
        return "SellerMenu";
    }

    @DeleteMapping("/delete")
    public String delete(
            @RequestParam UUID userId,
            @RequestParam UUID productId,
            Model model
    ) {
        Boolean aBoolean = productService.deleteById(productId, userId);
        if (aBoolean==null){
            model.addAttribute("message","Product not found");
            model.addAttribute("userId",userId);
            return "SellerMenu";
        }
        model.addAttribute("message","Product successfully deleted");
        model.addAttribute("userId",userId);
        return "SellerMenu";
    }


}
