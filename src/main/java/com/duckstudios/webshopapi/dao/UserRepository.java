package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.CustomUser;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    CustomUser findByEmail(String email);
    @Query("Select u.id FROM custom_user u WHERE u.email = :email")
    Long findCustomUserIdByEmail(@Param("email") String email);
    String email(String email);

    CustomUser findCustomUserById(long id);

    @Override
    @Nonnull
    List<CustomUser> findAll();

    void deleteCustomUserById(long id);
}
