package com.example.digital_shop.controller.product;


import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.exception.UnauthorizedAccessException;
import com.example.digital_shop.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductEntity> add(
            @RequestBody ProductCreatDto productCreatDto,
            @RequestParam UUID userId,
            @RequestParam Integer amount
    ){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Seller"))){
            throw new UnauthorizedAccessException("You don`t have permission to access this recourse");
        }
            return ResponseEntity.ok(productService.add(productCreatDto,userId,amount));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductEntity>> getAll(
            @RequestParam(required = false,defaultValue = "10") int size,
            @RequestParam(defaultValue = "0",required = false)  int page
    ){
        return ResponseEntity.ok(productService.getAllProducts(size, page));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> search(
            @RequestParam(defaultValue = "10",required = false) int size,
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam String name
    ){
        return ResponseEntity.status(200).body(productService.search(size, page, name));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ProductEntity> update(
            @PathVariable UUID userId,
            @RequestBody ProductCreatDto productCreatDto,
            @RequestParam UUID productId,
            @RequestParam Integer amount
            ){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Seller"))){
            throw new UnauthorizedAccessException("You don`t have permission to access this recourse");
        }
        return ResponseEntity.ok(productService.update(productCreatDto,productId,amount,userId));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID userId,
            @RequestParam UUID productId,
            HttpServletRequest request
    ){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Seller"))){
            throw new UnauthorizedAccessException("You don`t have permission to access this recourse");
        }
        return ResponseEntity.ok(productService.deleteById(productId,userId,request.getHeader("authorization")));
    }


}
