package com.recipehub.repository;

import com.recipehub.model.Puntuacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPuntuacion extends MongoRepository<Puntuacion, String> {
    
    /**
     * Obtiene todas las puntuaciones de una receta
     */
    List<Puntuacion> findByRecetaId(String recetaId);
    
    /**
     * Obtiene todas las puntuaciones de un usuario
     */
    List<Puntuacion> findByUsuarioId(String usuarioId);
    
    /**
     * Obtiene la puntuación de un usuario para una receta específica
     */
    Optional<Puntuacion> findByRecetaIdAndUsuarioId(String recetaId, String usuarioId);
    
    /**
     * Elimina todas las puntuaciones de una receta
     */
    void deleteByRecetaId(String recetaId);
    
    /**
     * Verifica si un usuario ha puntuado una receta
     */
    boolean existsByRecetaIdAndUsuarioId(String recetaId, String usuarioId);
}
