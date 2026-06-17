package com.Julienshop.webshopapi.services;


import com.Julienshop.webshopapi.dao.CustomUserDAO;
import com.Julienshop.webshopapi.dao.UserRepository;
import com.Julienshop.webshopapi.models.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
public class AuthenticationService {

    private final CredentialValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomUserDAO customUserDAO;



    public AuthenticationService(CredentialValidator validator, PasswordEncoder passwordEncoder,
                                 UserRepository userRepository, CustomUserDAO customUserDAO) {
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customUserDAO = customUserDAO;
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new IllegalStateException("No authenticated user found");
    }

    public CustomUser getAuthenticatedUser() {
        String currentPrincipalName = getCurrentUserEmail();
        return customUserDAO.getCustomUserByEmail(currentPrincipalName);
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

    public void validateIfUserIsIndeedTheUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        CustomUser customUser = customUserDAO.getCustomUserByEmail(currentPrincipalName);
    }


    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
