package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.config.JWTUtil;
import com.duckstudios.webshopapi.dao.CustomUserDAO;
import com.duckstudios.webshopapi.dto.AuthenticationDTO;
import com.duckstudios.webshopapi.dto.LoginResponse;
import com.duckstudios.webshopapi.models.CustomUser;
import com.duckstudios.webshopapi.models.enums.Role;
import com.duckstudios.webshopapi.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final AuthenticationService authenticationService;
    private final CustomUserDAO customUserDAO;

    public AuthController(JWTUtil jwtUtil, AuthenticationManager authManager,
                          AuthenticationService authenticationService, CustomUserDAO customUserDAO) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.authenticationService = authenticationService;
        this.customUserDAO = customUserDAO;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthenticationDTO authenticationDTO) {
        authenticationService.throwIfCustomUserExists(authenticationDTO.getEmail());
        authenticationService.validateEmail(authenticationDTO.getEmail());
        authenticationService.validatePassword(authenticationDTO.getPassword());
        CustomUser customUser = this.customUserDAO.createCustomUser(authenticationDTO.getEmail(), authenticationDTO.getPassword(), Role.CUSTOMER);
        return createLoginResponse(customUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword());
            authManager.authenticate(authInputToken);

            CustomUser customUser = customUserDAO.getCustomUserByEmail(authenticationDTO.getEmail());
            return createLoginResponse(customUser);

        } catch (AuthenticationException authExc) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

    private ResponseEntity<LoginResponse> createLoginResponse(CustomUser customUser) {
        String token = jwtUtil.generateToken(customUser.getEmail());
        LoginResponse loginResponse = new LoginResponse(customUser.getEmail(), token, customUser.getRole());
        return ResponseEntity.ok(loginResponse);
    }

}
