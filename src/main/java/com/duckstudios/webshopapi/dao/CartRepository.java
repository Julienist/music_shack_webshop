package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.Cart;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomUser(CustomUser customUser);
}
