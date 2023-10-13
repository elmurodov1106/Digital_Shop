package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.order.OrderService;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/add")
    public String add(
            @RequestBody OrderDto orderDto,
            @RequestParam Integer amount,
            HttpServletRequest request,
            Model model
    ){
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
         orderService.add(orderDto,userId,amount);
        UserEntity user= userService.getById(userId);
        model.addAttribute("user",user);
        return "index";
    }
    @GetMapping("/get-user-orders")
    public String getUserOrders(HttpServletRequest request,Model model){
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "index";
        }
        List<OrderEntity> userOrders = orderService.getUserOrders(userId);
        model.addAttribute("user",userService.getById(userId));
        if(userOrders.isEmpty()){
            model.addAttribute("message","You dont have any orders");
            return "index";
        }
        model.addAttribute("orders",userOrders);
        return "basket";
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<OrderEntity> update(
            @RequestBody OrderDto orderDto,
            @PathVariable UUID userId,
            @RequestParam UUID orderId
    ){
        return ResponseEntity.ok(orderService.update(orderDto,orderId,userId));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID userId,
            @RequestParam UUID orderId
    ){
        return ResponseEntity.ok(orderService.deleteById(orderId,userId));
    }
    @GetMapping("/get")
    public String basket() {
        return "basket";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(!userId.equals("null")){
            return UUID.fromString(userId);
        }
        return null;
    }


}
