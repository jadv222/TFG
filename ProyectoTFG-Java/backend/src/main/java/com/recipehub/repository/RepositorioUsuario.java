package com.recipehub.repository;

import com.recipehub.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RepositorioUsuario
 * Interfaz para acceder a datos de Usuario en MongoDB
 * Spring Data genera automáticamente las implementaciones
 */
@Repository
public interface RepositorioUsuario extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailAndRol(String email, String rol);
}
