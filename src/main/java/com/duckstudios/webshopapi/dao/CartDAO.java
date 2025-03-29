package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.CartDTO;
import com.duckstudios.webshopapi.models.Cart;
import com.duckstudios.webshopapi.models.CustomUser;
import com.duckstudios.webshopapi.services.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class CartDAO {

    private final CartRepository cartRepository;
    private final EntityValidator entityValidator;

    public CartDAO(CartRepository cartRepository, EntityValidator entityValidator) {
        this.cartRepository = cartRepository;
        this.entityValidator = entityValidator;
    }

    public List<Cart> getAllCarts() {
        return this.cartRepository.findAll();
    }

    public Optional<Cart> getCartById(long id) {
        entityValidator.checkIfIdExists(id, cartRepository, "Cart");
        return this.cartRepository.findById(id);
    }

//    public void createCart(CartDTO cartDTO) {
//    CustomUser customUserId = entityValidator.checkIfIdExists(id, UserRepository, "CustomUser");
// 1. Weten of CustomUserId bestaat
// 2. Weten of CartId al bestaat, G
// 2. Cart maken met customUserId
//        Cart cart = new Cart(cartDTO.customUserId);
//        this.cartRepository.save(cart);
//    }

    public void deleteCart(long id) {
        Cart cart = entityValidator.checkIfIdExists(id, cartRepository, "Cart");
        this.cartRepository.delete(cart);
    }
}
