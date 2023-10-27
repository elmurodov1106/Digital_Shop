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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public OrderEntity add(OrderDto orderDto) {
       OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
        return orderRepository.save(orderEntity);
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
        if (orderEntity.getUserId().getId().equals(userId)){
            orderRepository.deleteById(id);
            return true;
        }
        throw new DataNotFoundException("Order not found");
    }

    @Override
    public OrderEntity update(OrderDto update, UUID id, UUID userId) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (orderEntity.getUserId().getId().equals(userId)) {
            modelMapper.map(update, orderEntity);
            return orderRepository.save(orderEntity);
        }
        return null;
    }

    @Override
    public List<OrderEntity> getUserOrders(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()->new DataNotFoundException("User not found"));
        List<OrderEntity> orders = orderRepository.getOrderEntitiesByUserIdEquals(user);
        System.out.println(orders);
        if(orders.isEmpty()){
           return null;
        }
        return orders;
    }
}
