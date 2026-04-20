package com.recipehub.repository;

import com.recipehub.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 * Interfaz para acceder a datos de Usuario en MongoDB
 * Spring Data genera automáticamente las implementaciones
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
