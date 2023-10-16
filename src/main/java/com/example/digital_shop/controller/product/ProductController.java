package com.example.digital_shop.controller.product;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.service.product.ProductService;
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
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/add")
    public String addGet() {
        return "ProductAdd";
    }



    @PostMapping("/add")
    public String add(
            @ModelAttribute ProductCreatDto productCreatDto,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            HttpServletRequest request,
            Model model
    ) throws IOException {
        UUID userId= checkCookie(request);
        if(userId == null){
            return "index";
        }
       productService.add(productCreatDto,userId,amount,image);
       model.addAttribute("message","Product successfully added");
        return "SellerMenu";
    }

    @GetMapping("/get-all")
    public String getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        List<ProductEntity> all = productService.getAllProducts(size,page);
        if(all.isEmpty()){
            model.addAttribute("message","Product not found");
            return "index";
        }
        model.addAttribute("products",all);
        return "index";
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
            @RequestBody ProductCreatDto productCreatDto,
            @RequestParam UUID productId,
            @RequestParam Integer amount,
            @RequestParam MultipartFile image,
            HttpServletRequest request,
            Model model
    ) throws IOException {
        UUID userId= checkCookie(request);
        if(userId == null){
            return "index";
        }
        ProductEntity update = productService.update(productCreatDto, productId, amount, userId,image);
        if(update==null){
            model.addAttribute("message","Product not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Product successfully updated");
        return "SellerMenu";
    }

    @GetMapping("/delete")
    public String delete(
            @RequestParam UUID productId,
            Model model,
            HttpServletRequest request
    ) {
        UUID userId= checkCookie(request);
        if(userId == null){
            return "index";
        }
        Boolean aBoolean = productService.deleteById(productId, userId);
        if (aBoolean==null){
            model.addAttribute("message","Product not found");
            return "SellerMenu";
        }
        model.addAttribute("message","Product successfully deleted");
        return "SellerMenu";
    }
    @GetMapping("/get-seller-products")
    public String getSellerProducts(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            HttpServletRequest request,
            Model model){
       UUID sellerId = checkCookie(request);
       if(sellerId == null){
           model.addAttribute("message","Seller not found");
           return "index";
       }
       model.addAttribute("products",productService.getSellerProduct(sellerId,page,size));
       return "allProducts";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(!userId.equals("null")){
            return UUID.fromString(userId);
        }
        return null;
    }

}
