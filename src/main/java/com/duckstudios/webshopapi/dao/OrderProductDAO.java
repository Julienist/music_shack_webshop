package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.OrderProduct;
import com.duckstudios.webshopapi.services.EntityValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProductDAO {

    private final OrderProductRepository orderProductRepository;
    private final EntityValidator entityValidator;

    public OrderProductDAO(OrderProductRepository orderProductRepository, EntityValidator entityValidator) {
        this.orderProductRepository = orderProductRepository;
        this.entityValidator = entityValidator;
    }

//    public List<OrderProduct> getAllOrderProducts() {
//        return this.orderProductRepository.findAll();
//    }

//    public void createOrderProduct(OrderProduct orderProduct) {
//        this.orderProductRepository.save(orderProduct);
//    }

// Service in EntityValidator maken voor findByOrderIdAndProductId
//
//    public void deleteOrderProduct(long orderId, long productId) {
//        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId)
//                .orElseThrow(() -> new EntityNotFoundException("OrderProduct not found"));
//        this.orderProductRepository.delete(orderProduct);
//    }
}
