package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.OrderDTO;
import com.duckstudios.webshopapi.models.CustomUser;
import com.duckstudios.webshopapi.models.Order;
import com.duckstudios.webshopapi.services.EntityValidator;
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

    public List<Order> getAllOrders() {
        //noinspection UnnecessaryLocalVariable
        List<Order> orders = this.orderRepository.findAll();
        return orders;
    }

    public Optional<Order> getOrder(long id) {
        entityValidator.checkIfIdExists(id, orderRepository, "Order");
        //noinspection UnnecessaryLocalVariable
        Optional<Order> order = this.orderRepository.findById(id);
        // Opgesplitst in 2 lines, voor leesbaarheid
        return order;
    }

    public void createOrder(OrderDTO orderDTO) {
        CustomUser customuserId = entityValidator.checkIfIdExists(orderDTO.customuserId, userRepository, "CustomuserId");
        Order order = new Order(customuserId, orderDTO.orderDate, orderDTO.orderStatus, orderDTO.totalPrice);
        this.orderRepository.save(order);
    }
}
