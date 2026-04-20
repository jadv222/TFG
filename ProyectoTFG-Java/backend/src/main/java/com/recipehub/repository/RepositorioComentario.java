package com.recipehub.repository;

import com.recipehub.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioComentario extends MongoRepository<Comentario, String> {
    
    /**
     * Obtiene todos los comentarios de una receta ordenados por fecha (más recientes primero)
     */
    List<Comentario> findByRecetaIdOrderByFechaCreacionDesc(String recetaId);
    
    /**
     * Obtiene todos los comentarios de un usuario
     */
    List<Comentario> findByUsuarioId(String usuarioId);
    
    /**
     * Obtiene los comentarios de un usuario en una receta específica
     */
    List<Comentario> findByRecetaIdAndUsuarioId(String recetaId, String usuarioId);
    
    /**
     * Elimina todos los comentarios de una receta
     */
    void deleteByRecetaId(String recetaId);
    
    /**
     * Verifica si un usuario ha comentado una receta
     */
    boolean existsByRecetaIdAndUsuarioId(String recetaId, String usuarioId);
}
