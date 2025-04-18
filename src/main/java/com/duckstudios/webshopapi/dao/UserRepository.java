package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.CustomUser;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findUserById(UUID id);
    Optional<CustomUser> findUserByEmail(String email);

    @Override
    @Nonnull
    List<CustomUser> findAll();

    void deleteCustomUserById(UUID id);
}
