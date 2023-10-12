package com.example.digital_shop.controller;

import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<OrderEntity> add(
            @RequestBody OrderDto orderDto,
            @PathVariable UUID userId,
            @RequestParam Integer amount
    ){
        return ResponseEntity.ok(orderService.add(orderDto,userId,amount));
    }
    @GetMapping("/{userId}/get-user-orders")
    public ResponseEntity<List<OrderEntity>> getUserOrders(@PathVariable UUID userId){
        return ResponseEntity.ok(orderService.getUserOrders(userId));
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



}
