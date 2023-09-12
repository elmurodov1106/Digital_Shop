package com.example.digital_shop.service.order;



import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderEntity add(OrderDto orderDto, UUID userId, Integer amount);
    List<OrderEntity> getAllProducts(int size, int page);
    Boolean deleteById(UUID id,UUID userId);
    OrderEntity update(OrderDto update,UUID id,UUID userId);
    List<OrderEntity> getUserOrders(UUID userId);


}
