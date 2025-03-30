package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.OrderEntity;
import com.duckstudios.webshopapi.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    Optional<OrderProduct> findByOrderIdAndProductId(Long orderId, Long productId);
}

