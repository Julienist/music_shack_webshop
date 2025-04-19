package com.Julienshop.webshopapi.services;

import com.Julienshop.webshopapi.dao.CartProductRepository;
import com.Julienshop.webshopapi.dao.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntityValidator {

    private final OrderProductRepository orderProductRepository;
    private final CartProductRepository cartProductRepository;

    @Autowired
    public EntityValidator(@Lazy OrderProductRepository orderProductRepository,
                           @Lazy CartProductRepository cartProductRepository) {
        this.orderProductRepository = orderProductRepository;
        this.cartProductRepository = cartProductRepository;
    }

    public <T, ID> T checkIfIdExists(ID id, JpaRepository<T, ID> repository, String entityName) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " met id " + id + " bestaat niet!"));
    }

    public boolean checkOrderProductExists(long orderId, long productId) {
        return orderProductRepository.findByOrderIdAndProductId(orderId, productId).isPresent();
    }

    public boolean checkCartProductExists(long cartId, long productId) {
        return cartProductRepository.findByCartIdAndProductId(cartId, productId).isPresent();
    }
}
