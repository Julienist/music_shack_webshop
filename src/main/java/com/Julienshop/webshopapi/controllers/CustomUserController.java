package com.Julienshop.webshopapi.controllers;

import com.Julienshop.webshopapi.dao.CustomUserDAO;
import com.Julienshop.webshopapi.dto.AuthenticationDTO;
import com.Julienshop.webshopapi.dto.UpdateAccountDTO;
import com.Julienshop.webshopapi.models.CustomUser;
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

    @PutMapping("/{email}")
    public ResponseEntity<String> updateUserData(@RequestBody AuthenticationDTO authenticationDTO, @RequestBody UpdateAccountDTO updateAccountDTO) {
        CustomUser user = customUserDAO.getCustomUserByEmail(authenticationDTO.getEmail());
        this.customUserDAO.updateCustomUser(user, updateAccountDTO);
        return ResponseEntity.ok("Updated user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_user_{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        this.customUserDAO.deleteCustomUserById(id);
        return ResponseEntity.ok("User deleted!");
    }
}
