package com.example.digital_shop.controller;

import com.example.digital_shop.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PutMapping("/transaction")
    public String p2p(@RequestParam UUID sender,
                      @RequestParam UUID receiver,
                      @RequestParam Double amount,
                      @RequestParam UUID productId){
        transactionService.transferMoney(sender,receiver,amount,productId);
        return "Ishladi";
    }
}
