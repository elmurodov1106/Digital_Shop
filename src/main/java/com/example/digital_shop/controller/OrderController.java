package com.example.digital_shop.controller;

import com.example.digital_shop.config.CookieValue;
import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.service.order.OrderService;
import com.example.digital_shop.service.product.ProductService;
import com.example.digital_shop.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
            @ModelAttribute OrderDto orderDto,
            HttpServletRequest request,
            Model model
    ){
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "signIn";
        }
        orderDto.setUserId(userId);
         orderService.add(orderDto);
        UserEntity user= userService.getById(userId);
        model.addAttribute("user",user);
        return "redirect:/auth/index";
    }
    @GetMapping("/get-user-orders")
    public String getUserOrders(
            HttpServletRequest request,
            Model model){
        UUID userId=checkCookie(request);
        if(userId ==null){
            return "signIn";
        }
        List<OrderEntity> userOrders = orderService.getUserOrders(userId);
        model.addAttribute("user",userService.getById(userId));
        if(userOrders == null){
            model.addAttribute("user",userService.getById(userId));
            model.addAttribute("message","You dont have any orders");
            return "basket";
        }
        model.addAttribute("orders",userOrders);
//        model.addAttribute("orders",getOrder(userOrders));
//        model.addAttribute("products",getAll(userOrders));
        return "basket";
    }
    @PostMapping("/update")
    public String update(
            @RequestBody OrderDto orderDto,
            @RequestParam UUID orderId,
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        if(userId == null){
            return "index";
        }
        orderService.update(orderDto,orderId,userId);
        model.addAttribute("message","Updated");
        return "basket";
    }

    @GetMapping("/delete")
    public String delete(
            @RequestParam UUID orderId,
            HttpServletRequest request,
            Model model
    ){
        UUID userId = checkCookie(request);
        Boolean aBoolean = orderService.deleteById(orderId, userId);
        List<OrderEntity> userOrders = orderService.getUserOrders(userId);
        if(aBoolean){
            model.addAttribute("message","Order Succefully deleted");
            return "basket";
        }
        model.addAttribute("message","Error deleting order");
        return "basket";
    }
    private UUID checkCookie(HttpServletRequest request){
        String userId = CookieValue.getValue("userId",request);
        if(userId!= null){
            return UUID.fromString(userId);
        }
        return null;
    }

//    private OrderEntity getOrder(List<OrderEntity> orders){
//        for(OrderEntity order : orders) {
//        return order;
//        }
//        return null;
//    }

}
