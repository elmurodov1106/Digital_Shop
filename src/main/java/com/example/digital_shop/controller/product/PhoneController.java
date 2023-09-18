package com.example.digital_shop.controller.product;


import com.example.digital_shop.domain.dto.PhoneDto;
import com.example.digital_shop.entity.product.PhoneEntity;
import com.example.digital_shop.exception.UnauthorizedAccessException;
import com.example.digital_shop.service.phone.PhoneService;
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
@RequestMapping("/product/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping("{userId}/add")
    public ResponseEntity<PhoneEntity> add(
            @RequestBody PhoneDto phoneDto,
            @PathVariable UUID userId,
            @RequestParam Integer amount,
            HttpServletRequest request

    ){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Seller"))){
            throw new UnauthorizedAccessException("You don`t have permission to access this recourse");
        }
        return ResponseEntity.ok(phoneService.add(phoneDto,userId,amount,request.getHeader("authorization")));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PhoneEntity>> getAll(
            @RequestParam int size,
            @RequestParam int page
    ){
        return ResponseEntity.ok(phoneService.getAllPhone(size,page));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhoneEntity>> search(
            @RequestParam int size,
            @RequestParam int page,
            @RequestParam String name
    ){
        return ResponseEntity.status(200).body(phoneService.search(size, page, name));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<PhoneEntity> update(
            @RequestBody PhoneDto phoneDto,
            @PathVariable UUID userId,
            @RequestParam UUID phoneId
    ){
        return ResponseEntity.ok(phoneService.update(phoneDto,phoneId,userId));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID userId,
            @RequestParam UUID phoneId,
            HttpServletRequest request
    ){
        return ResponseEntity.ok(phoneService.deleteById(phoneId,userId,request.getHeader("authorization")));
    }









}
