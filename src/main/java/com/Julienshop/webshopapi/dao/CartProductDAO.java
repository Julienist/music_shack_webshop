package com.Julienshop.webshopapi.dao;

import com.Julienshop.webshopapi.dto.CartProductDTO;
import com.Julienshop.webshopapi.models.CartProduct;
import com.Julienshop.webshopapi.services.EntityValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public Optional<CartProduct> getCartProductById(long id) {
        entityValidator.checkIfIdExists(id, cartProductRepository, "CartProduct");
        Optional<CartProduct> cartProduct = this.cartProductRepository.findById(id);
        return cartProduct;
    }

    public void createCartProduct(CartProductDTO cartProductDTO) {
        CartProduct cartProduct = new CartProduct(cartProductDTO.getCart(), cartProductDTO.getProduct(), cartProductDTO.getQuantity(), cartProductDTO.getTotalPrice());
        this.cartProductRepository.save(cartProduct);
    }

    public void deleteCartProduct(long cartId, long productId) {
        if (!entityValidator.checkOrderProductExists(cartId, productId)) {
            throw new EntityNotFoundException("cartProduct not found");
        }

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cartId, productId).get();
        this.cartProductRepository.delete(cartProduct);
    }
}
