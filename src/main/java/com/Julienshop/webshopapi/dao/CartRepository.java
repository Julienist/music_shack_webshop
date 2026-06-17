package com.Julienshop.webshopapi.dao;

import com.Julienshop.webshopapi.models.Cart;
import com.Julienshop.webshopapi.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomUser(Optional<CustomUser> customUser);
}
