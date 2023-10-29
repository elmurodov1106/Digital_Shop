package com.example.digital_shop.repository.order;

import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.entity.user.UserEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;



@RestController
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> getOrderEntitiesByUserIdEquals(UUID userId);

    @Query("SELECT o FROM orders o WHERE o.id = :order_id AND o.userId = :user_id")
    OrderEntity getUserOrder(@Param("user_id") UUID userId, @Param("order_id") UUID id);
    @Query("SELECT o FROM orders o WHERE o.product.id = :product_id AND o.userId = :user_id")
    OrderEntity getUserAndProduct(@Param("user_id") UUID userId, @Param("product_id") UUID product_id);
    OrderEntity findOrderEntityByUserIdAndIdEquals(UserEntity user, UUID orderId);
}
