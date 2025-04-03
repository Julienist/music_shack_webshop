package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.UserRepository;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class CustomUserController {

    private final UserRepository userDAO;


    public CustomUserController(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public ResponseEntity<List<CustomUser>> getAllUsers() {
        List<CustomUser> users = userDAO.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/id/{email}")
    public ResponseEntity<Long> getUserIdByEmail(@PathVariable String email) {
//        Optional<Long> id = userDAO.findCustomUserIdByEmail(email);
        Long id = userDAO.findCustomUserIdByEmail(email);

        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(id);
    }

    @GetMapping("/user_of_{id}")
    public ResponseEntity<Optional<CustomUser>> getUserById(@PathVariable long id) {
        CustomUser user = userDAO.findCustomUserById(id);
        return ResponseEntity.ok(Optional.ofNullable(user));
    }

    @DeleteMapping("/delete_user_{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        this.userDAO.deleteCustomUserById(id);
        return ResponseEntity.ok("User deleted!");
    }
}
