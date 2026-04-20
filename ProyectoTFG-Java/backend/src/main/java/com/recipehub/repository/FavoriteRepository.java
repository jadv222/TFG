package com.recipehub.repository;

import com.recipehub.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FavoriteRepository
 * Interfaz para acceder a datos de Favoritos en MongoDB
 */
@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    List<Favorite> findByUsuarioIdOrderByFechaGuardadaDesc(String usuarioId);
    void deleteByIdAndUsuarioId(String id, String usuarioId);
}
