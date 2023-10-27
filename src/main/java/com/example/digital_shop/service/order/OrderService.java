package com.example.digital_shop.service.order;



import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;
import jakarta.persistence.criteria.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderEntity add(OrderEntity orderDto);
    List<OrderEntity> getAllProducts(int size, int page);
    Boolean deleteById(UUID id,UUID userId);
    OrderEntity update(OrderDto update,UUID id,UUID userId);
    List<OrderEntity> getUserOrders(UUID userId);
    OrderEntity getUserOrder(UUID userId, UUID orderId);


}
