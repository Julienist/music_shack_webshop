package com.Julienshop.webshopapi.dao;

import com.Julienshop.webshopapi.models.CustomUser;
import com.Julienshop.webshopapi.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findOrderEntityByCustomUser(CustomUser customUser);
}
