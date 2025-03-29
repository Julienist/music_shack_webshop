package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.CartProductDTO;
import com.duckstudios.webshopapi.models.CartProduct;
import com.duckstudios.webshopapi.services.EntityValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
public class CartProductDAO {

    private final CartProductRepository cartProductRepository;
    private final EntityValidator entityValidator;

    public CartProductDAO(CartProductRepository cartProductRepository, EntityValidator entityValidator) {
        this.cartProductRepository = cartProductRepository;
        this.entityValidator = entityValidator;
    }

    public List<CartProduct> getAllCartProducts() {
        return this.cartProductRepository.findAll();
    }

//    public void createCartProduct(CartProductDTO cartProductDTO) {
//        entityValidator.checkIfIdExists(cartProductDTO.id, cartProductRepository, "CartProduct");
//        this.cartProductRepository.save();
//    }


// functie hieronder heeft EntityValidator nodig, maar voor 2 Id validators uit 2 verschillende Entities.
//
//    public void deleteCartProduct(long cartId, long productId) {
//        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cartId, productId)
//                .orElseThrow(() -> new EntityNotFoundException("CartProduct not found"));
//        this.cartProductRepository.delete(cartProduct);
//    }
}
