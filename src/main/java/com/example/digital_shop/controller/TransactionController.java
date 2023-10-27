package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.service.order.OrderService;
import com.example.digital_shop.service.transaction.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final OrderService orderService;

    @GetMapping("/transaction")
    public String TransactionPage(@RequestParam UUID orderId, Model model, HttpServletRequest request) {
        UUID userId = checkCookie(request);
        if(userId==null){
            return "signIn";
        }
        OrderEntity userOrder = orderService.getUserOrder(userId, orderId);
        if(userOrder == null){
            model.addAttribute("message","Order not found");
            return "basket";
        }
        model.addAttribute("order",userOrder);
        return "buy";
    }

    @PostMapping("/create")
    public String p2p(@RequestParam UUID sender,
                      @RequestParam Double amount,
                      @RequestParam UUID productId){
        transactionService.transferMoney(sender,amount,productId);
        return "buy";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!=null){
            return UUID.fromString(userId);
        }
        return null;
    }
}
