package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.CartDTO;
import com.duckstudios.webshopapi.models.Cart;
import com.duckstudios.webshopapi.services.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public void createCart(CartDTO cartDTO) {
        //note:
        // 1. waarschijnlijk wil je ook checken of een customUser wel bestaat.
        // 2. Producten wil je in een cart zetten, je wilt dus niet hier een table vullen wss.
        Cart cart = new Cart(cartDTO.cartProducts,cartDTO.customUser,cartDTO.isActive);
        this.cartRepository.save(cart);
    }

    public void deleteCart(long id) {
        Cart cart = entityValidator.checkIfIdExists(id, cartRepository, "Cart");
        this.cartRepository.delete(cart);
    }
}
