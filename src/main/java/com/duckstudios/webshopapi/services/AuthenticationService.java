package com.duckstudios.webshopapi.services;


import com.duckstudios.webshopapi.dao.UserRepository;
import com.duckstudios.webshopapi.models.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
public class AuthenticationService {

    private final CredentialValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;



    public AuthenticationService(CredentialValidator validator,
                                 PasswordEncoder passwordEncoder,
                                 UserRepository userRepository) {
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void validateEmail(String email) {
        if (!validator.isValidEmail(email))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid email provided"
            );
    }

    public void validatePassword(String password) {
        if (!validator.isValidPassword(password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No valid password provided"
            );
        }
    }

    public void throwIfCustomUserExists(String email) {
        Optional<CustomUser> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot register with this email"
            );
        }
    }


    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
