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

//    public void createCartProduct(CartProductDTO cartProductDTO) {
//        // Check if the cart and product exist
//        if (!entityValidator.checkCartExists(cartProductDTO.getCartId())) {
//            throw new EntityNotFoundException("cart not found");
//        }
//        if (!entityValidator.checkProductExists(cartProductDTO.getProductid())) {
//            throw new EntityNotFoundException("product not found");
//        }
//        // Create a new CartProduct object
//        CartProductDTO cartProduct = new CartProduct(cartProductDTO.getCartId(), cartProductDTO.getProductid(), cartProductDTO.getQuantity(), cartProductDTO.getTotalPrice());
//        // Save the CartProduct object to the database
//        this.cartProductRepository.save(cartProduct);
//    }

//    public void deleteCartProduct(long cartId, long productId) {
//        if (!entityValidator.checkOrderProductExists(cartId, productId)) {
//            throw new EntityNotFoundException("cartProduct not found");
//        }
//
//        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cartId, productId).isPresent();
//        this.cartProductRepository.delete(cartProduct);
//    }
}
