package com.recipehub.repository;

import com.recipehub.model.RecetaPersonal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepositorioRecetaPersonal
 * Interfaz para acceder a datos de Recetas Personales en MongoDB
 */
@Repository
public interface RepositorioRecetaPersonal extends MongoRepository<RecetaPersonal, String> {
    List<RecetaPersonal> findByUsuarioIdOrderByFechaCreacionDesc(String usuarioId);
    RecetaPersonal findByIdAndUsuarioId(String id, String usuarioId);
    void deleteByIdAndUsuarioId(String id, String usuarioId);
}
