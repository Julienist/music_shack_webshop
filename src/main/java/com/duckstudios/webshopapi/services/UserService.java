package com.duckstudios.webshopapi.services;

import com.duckstudios.webshopapi.dao.UserRepository;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<CustomUser> customUser = userRepository.findUserByEmail(email);

        if (customUser.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }   return User.builder()
        .username(customUser.get().getEmail())
        .password(customUser.get().getPassword())
        .roles(String.valueOf(customUser.get().getRole()))
        .build();
    }
}
