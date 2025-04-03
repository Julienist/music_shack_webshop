package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.OrderDTO;
import com.duckstudios.webshopapi.models.CustomUser;
import com.duckstudios.webshopapi.models.OrderEntity;
import com.duckstudios.webshopapi.services.EntityValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {

    private final OrderRepository orderRepository;
    private final EntityValidator entityValidator;
    private final UserRepository userRepository;

    public OrderDAO(OrderRepository orderRepository, EntityValidator entityValidator, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.entityValidator = entityValidator;
        this.userRepository = userRepository;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
//        List<OrderEntity> orders = this.orderRepository.findAll();
//        return orders;
    }

    public Optional<OrderEntity> getOrderById(long id) {
        entityValidator.checkIfIdExists(id, orderRepository, "Order");
        return orderRepository.findById(id);
//        Optional<OrderEntity> order = this.orderRepository.findById(id);
        // Opgesplitst in 2 lines, voor leesbaarheid
//        return order;
    }

    public List<OrderEntity> getOrdersForCurrentUser(String email) {
        CustomUser customUser = userRepository.findByEmail(email);
        return orderRepository.findOrderEntityByCustomUser(customUser);
    }

    public void createOrder(OrderDTO orderDTO) {
        CustomUser customuserId = entityValidator.checkIfIdExists(orderDTO.customUserId, userRepository, "CustomuserId");
        OrderEntity order = new OrderEntity(customuserId, orderDTO.orderDate, orderDTO.orderStatus, orderDTO.totalPrice, orderDTO.orderProducts);
        this.orderRepository.save(order);
    }

    @Transactional
    public void deleteOrderById(long id) {
        OrderEntity order = entityValidator.checkIfIdExists(id, orderRepository, "OrderEntity");
        this.orderRepository.delete(order);
    }
}
