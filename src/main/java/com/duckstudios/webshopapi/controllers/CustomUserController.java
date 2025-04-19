package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.CustomUserDAO;
import com.duckstudios.webshopapi.dto.AuthenticationDTO;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class CustomUserController {
    private final CustomUserDAO customUserDAO;

    public CustomUserController(CustomUserDAO customUserDAO) {
        this.customUserDAO = customUserDAO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomUser>> getAllUsers() {
        List<CustomUser> users = customUserDAO.findAll();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/id/{email}")
    public UUID getUserIdByEmail(@PathVariable String email) {
        return this.customUserDAO.getCustomUserIdByEmail(email);
    }

    @GetMapping("/user_of_{id}")
    public ResponseEntity<Optional<CustomUser>> getUserById(@PathVariable UUID id) {
        CustomUser user = customUserDAO.getCustomUserById(id);
        return ResponseEntity.ok(Optional.ofNullable(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserData(@RequestBody AuthenticationDTO accountdata, @PathVariable String id) {
        this.customUserDAO.updateCustomUser(accountdata);
        return ResponseEntity.ok("Updated user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_user_{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        this.customUserDAO.deleteCustomUserById(id);
        return ResponseEntity.ok("User deleted!");
    }
}
