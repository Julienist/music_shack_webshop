package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.CartDAO;
import com.duckstudios.webshopapi.dto.AuthenticationDTO;
import com.duckstudios.webshopapi.dto.CartDTO;
import com.duckstudios.webshopapi.models.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/carts")
public class CartController {

    private final CartDAO cartDAO;

    public CartController(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @GetMapping("/my_cart")
    public ResponseEntity<List<Cart>>  getCartForUser(@RequestBody AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok(Collections.singletonList(cartDAO.getCartForCurrentUser(authenticationDTO.getEmail())));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(this.cartDAO.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cart>> getCartById(@PathVariable long id) {
        return ResponseEntity.ok(this.cartDAO.getCartById(id));
    }

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody CartDTO cartDTO) {
        this.cartDAO.createCart(cartDTO);
        return ResponseEntity.ok("Cart created!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable long id) {
        this.cartDAO.deleteCart(id);
        return ResponseEntity.ok("Cart deleted!");
    }

}
