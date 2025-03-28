package com.duckstudios.webshopapi.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntityValidator {

    public <T, ID> T checkIfIdExists(ID id, JpaRepository<T, ID> repository, String entityName) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " met id " + id + " bestaat niet!"));
    }
}
