package com.example.digital_shop.service.order;

import com.example.digital_shop.domain.dto.OrderDto;
import com.example.digital_shop.entity.order.OrderEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.UserRepository;
import com.example.digital_shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public OrderEntity add(OrderEntity orderDto) {
        return orderRepository.save(orderDto);
    }

    @Override
    public List<OrderEntity> getAllProducts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<OrderEntity> content = orderRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Orders not found");
        }
        return content;
    }



    @Override
    public Boolean deleteById(UUID id, UUID userId) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (orderEntity.getUserId().equals(userId)){
            orderRepository.deleteById(id);
            return true;
        }
        throw new DataNotFoundException("Order not found");
    }

    @Override
    public OrderEntity update(OrderDto update, UUID id, UUID userId) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (orderEntity.getUserId().equals(userId)) {
            modelMapper.map(update, orderEntity);
            return orderRepository.save(orderEntity);
        }
        return null;
    }

    @Override
    public List<OrderEntity> getUserOrders(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new DataNotFoundException("User not found"));
        List<OrderEntity> orders = orderRepository.getOrderEntitiesByUserIdEquals(user.getId());
        System.out.println(orders);
        if(orders.isEmpty()){
           return null;
        }
        return orders;
    }

    @Override
    public OrderEntity getUserOrder(UUID userId, UUID orderId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        UserEntity user;
        if (byId.isPresent()){
           user = byId.get();
        }else {
            return null;
        }
       return orderRepository.findOrderEntityByUserIdAndIdEquals(user,orderId);
    }
}
