package com.example.digital_shop.controller;

import com.example.digital_shop.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;


}
