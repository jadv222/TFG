package com.recipehub.service;

import com.recipehub.model.Puntuacion;
import com.recipehub.repository.RepositorioPuntuacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ServicioPuntuacion
 * Servicio para gestionar puntuaciones de recetas
 */
@Service
public class ServicioPuntuacion {

    @Autowired
    private RepositorioPuntuacion repositorioPuntuacion;

    /**
     * Crea o actualiza una puntuación (un usuario puede puntuarse una receta una sola vez)
     */
    public Puntuacion guardarPuntuacion(String recetaId, String usuarioId, Integer puntuacion) throws Exception {
        // Validar que la puntuación esté entre 1 y 5
        if (puntuacion < 1 || puntuacion > 5) {
            throw new Exception("La puntuación debe ser entre 1 y 5");
        }
        
        // Buscar si el usuario ya ha puntuado esta receta
        Optional<Puntuacion> puntuacionExistente = repositorioPuntuacion.findByRecetaIdAndUsuarioId(recetaId, usuarioId);
        
        Puntuacion nuevaPuntuacion;
        if (puntuacionExistente.isPresent()) {
            // Actualizar puntuación existente
            nuevaPuntuacion = puntuacionExistente.get();
            nuevaPuntuacion.setPuntuacion(puntuacion);
            nuevaPuntuacion.setFechaModificacion(System.currentTimeMillis());
        } else {
            // Crear nueva puntuación
            nuevaPuntuacion = new Puntuacion(recetaId, usuarioId, puntuacion);
        }
        
        return repositorioPuntuacion.save(nuevaPuntuacion);
    }

    /**
     * Obtiene el promedio de puntuaciones de una receta
     */
    public Double obtenerPromedioPuntuaciones(String recetaId) {
        List<Puntuacion> puntuaciones = repositorioPuntuacion.findByRecetaId(recetaId);
        
        if (puntuaciones.isEmpty()) {
            return 0.0;
        }
        
        Double suma = puntuaciones.stream()
                .mapToDouble(Puntuacion::getPuntuacion)
                .sum();
        
        return suma / puntuaciones.size();
    }

    /**
     * Obtiene la cantidad de puntuaciones de una receta
     */
    public Long obtenerCantidadPuntuaciones(String recetaId) {
        return (long) repositorioPuntuacion.findByRecetaId(recetaId).size();
    }

    /**
     * Obtiene la puntuación de un usuario para una receta
     */
    public Optional<Puntuacion> obtenerPuntuacionUsuario(String recetaId, String usuarioId) {
        return repositorioPuntuacion.findByRecetaIdAndUsuarioId(recetaId, usuarioId);
    }

    /**
     * Elimina la puntuación de un usuario
     */
    public void eliminarPuntuacion(String recetaId, String usuarioId) throws Exception {
        Optional<Puntuacion> puntuacion = repositorioPuntuacion.findByRecetaIdAndUsuarioId(recetaId, usuarioId);
        
        if (puntuacion.isPresent()) {
            repositorioPuntuacion.deleteById(puntuacion.get().getId());
        } else {
            throw new Exception("Puntuación no encontrada");
        }
    }

    /**
     * Obtiene todas las puntuaciones de una receta
     */
    public List<Puntuacion> obtenerPuntuacionesReceta(String recetaId) {
        return repositorioPuntuacion.findByRecetaId(recetaId);
    }

    /**
     * Obtiene todas las puntuaciones de un usuario
     */
    public List<Puntuacion> obtenerPuntuacionesUsuario(String usuarioId) {
        return repositorioPuntuacion.findByUsuarioId(usuarioId);
    }

    /**
     * Elimina todas las puntuaciones de una receta (usado cuando se elimina la receta)
     */
    public void eliminarPuntuacionesReceta(String recetaId) {
        repositorioPuntuacion.deleteByRecetaId(recetaId);
    }
}
