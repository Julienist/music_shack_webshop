package com.duckstudios.webshopapi.services;

import com.duckstudios.webshopapi.dao.UserRepository;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        CustomUser customUser = userRepository.findByEmail(email);
//        return new User(
//                email,
//                customUser.getPassword(),
//                Collections.singleton(new SimpleGrantedAuthority("ROLE USER")));
//        // role uit db halen.
//    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(email);

        if (customUser == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        // Haal de rol van de gebruiker op en zet deze correct in Spring Security formaat
        String roleName = "ROLE_" + customUser.getRole().name();

        return new User(
                customUser.getEmail(),
                customUser.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(roleName))
        );
    }
}
