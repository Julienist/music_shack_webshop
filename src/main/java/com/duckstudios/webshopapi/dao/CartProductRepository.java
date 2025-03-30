package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.Cart;
import com.duckstudios.webshopapi.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByCartIdAndProductId(Long cartId, Long productId);

    Long cart(Cart cart);
}
