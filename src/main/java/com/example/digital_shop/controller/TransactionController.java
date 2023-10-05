package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.TransactionDto;
import com.example.digital_shop.service.transaction.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping("/save")
    public String save(
            @RequestBody TransactionDto transactionDto,
            HttpServletRequest request
    ){
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        transactionService.save(transactionDto,userId);
        return "";
    }
    @GetMapping("/getAllTransactions")
    public String getAllTransaction(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size
    ){
        transactionService.getAllTransactions(page,size);
        return "";
    }
    @DeleteMapping("/delete")
    public String delete(
            @RequestParam UUID transactionId,
            HttpServletRequest request
            ){
        UUID userId=UUID.fromString(CookieValue.getValue("userId",request));
        transactionService.deleteById(transactionId,userId);
        return "";
    }
}
