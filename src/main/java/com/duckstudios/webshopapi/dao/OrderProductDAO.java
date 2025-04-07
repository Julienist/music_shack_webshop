package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.OrderProductDTO;
import com.duckstudios.webshopapi.models.CartProduct;
import com.duckstudios.webshopapi.models.OrderProduct;
import com.duckstudios.webshopapi.services.EntityValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderProductDAO {

    private final OrderProductRepository orderProductRepository;
    private final EntityValidator entityValidator;

    public OrderProductDAO(OrderProductRepository orderProductRepository, EntityValidator entityValidator) {
        this.orderProductRepository = orderProductRepository;
        this.entityValidator = entityValidator;
    }

    public List<OrderProduct> getAllOrderProducts() {
        return this.orderProductRepository.findAll();
    }

    public Optional<OrderProduct> getOrderProductById(long id) {
        entityValidator.checkIfIdExists(id, orderProductRepository, "OrderProduct");
        Optional<OrderProduct> orderProduct = this.orderProductRepository.findById(id);
        return orderProduct;
    }

//    public void createOrderProduct(OrderProductDTO orderProductDTO) {
//        OrderProduct orderProduct = new OrderProduct(orderProductDTO.order, orderProductDTO.product, orderProductDTO.quantity, orderProductDTO.totalPrice);
//        this.orderProductRepository.save(orderProduct);
//    }

    public void deleteOrderProduct(long orderId, long productId) {
        if (!entityValidator.checkOrderProductExists(orderId, productId)) {
            throw new EntityNotFoundException("OrderProduct not found");
        }

        OrderProduct orderProduct = orderProductRepository.findByOrderIdAndProductId(orderId, productId).get();
        this.orderProductRepository.delete(orderProduct);
    }




}
