package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.dto.UpdateAccountDTO;
import com.duckstudios.webshopapi.models.CustomUser;
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

    public CustomUser getCustomUserById(UUID id) {
        Optional<CustomUser> user = userRepository.findUserById(id);
        return getUserOrThrow(user);
    }

    public CustomUser getCustomUserByEmail(String email) {
        Optional<CustomUser> user = userRepository.findUserByEmail(email);
        return getUserOrThrow(user);
    }

    private CustomUser getUserOrThrow(Optional<CustomUser> user) {
        return user.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        ));
    }

    public UUID getCustomUserIdByEmail(String email) {
        return getCustomUserByEmail(email).getId();
    }

    public CustomUser createCustomUser(String email, String rawPassword) {
        CustomUser user = new CustomUser(email, passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return user;
    }

    public void updateCustomUser(CustomUser user, UpdateAccountDTO updateAccountDTO) {
        user.setEmail(updateAccountDTO.getEmail());
        user.setPassword(updateAccountDTO.getPassword());
        this.userRepository.save(user);
    }

    public void deleteCustomUserById(UUID id) {
        Optional<CustomUser> user = Optional.ofNullable(this.getCustomUserById(id));
        user.ifPresent(customUser -> userRepository.deleteCustomUserById(customUser.getId()));
    }
}
