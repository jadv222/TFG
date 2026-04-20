package com.recipehub.repository;

import com.recipehub.model.Favorito;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositorioFavorito
 * Interfaz para acceder a datos de Favoritos en MongoDB
 */
@Repository
public interface RepositorioFavorito extends MongoRepository<Favorito, String> {
    List<Favorito> findByUsuarioIdOrderByFechaGuardadaDesc(String usuarioId);
    void deleteByIdAndUsuarioId(String id, String usuarioId);
}
