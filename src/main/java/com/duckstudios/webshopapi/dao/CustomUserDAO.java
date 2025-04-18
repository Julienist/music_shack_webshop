package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.CustomUser;
import com.duckstudios.webshopapi.models.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomUserDAO {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDAO(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomUser> findAll() {
        return userRepository.findAll();
    }

//    public CustomUser getCustomUserById(long id) {
//        Optional<CustomUser> user = userRepository.findById(id);
//        if (user.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "User not found"
//            );
//        }
//        return user.get();
//    }

    public CustomUser getCustomUserById(UUID id) {
        Optional<CustomUser> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
        return user.get();
    }

    public CustomUser getCustomUserByEmail(String email) {
        Optional<CustomUser> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
            );
        }
        return user.get();
    }

    public UUID getCustomUserIdByEmail(String email) {
        return getCustomUserByEmail(email).getId();
    }

    public CustomUser createCustomUser(String email, String rawPassword, Role role) {
        CustomUser user = new CustomUser(email, passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return user;
    }

//    public CustomUser updateCustomUser(CustomUser customUser) {
//
//    }
    public void deleteCustomUserById(UUID id) {
        Optional<CustomUser> user = userRepository.findUserById(id);
//        if (user.isEmpty()) {
////            throw new
//        }
        userRepository.deleteCustomUserById(id);
    }
}
