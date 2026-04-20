package com.recipehub.service;

import com.recipehub.model.Favorito;
import com.recipehub.repository.RepositorioFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ServicioFavorito
 * Servicio para gestionar recetas favoritas
 */
@Service
public class ServicioFavorito {

    @Autowired
    private RepositorioFavorito favoriteRepository;

    /**
     * Obtiene todos los favoritos de un usuario
     */
    public List<Favorito> obtenerFavoritos(String usuarioId) {
        return favoriteRepository.findByUsuarioIdOrderByFechaGuardadaDesc(usuarioId);
    }

    /**
     * Guarda una receta como favorita
     */
    public Favorito guardarFavorito(String usuarioId, String spoonacularId, 
                                   String titulo, String imagen, 
                                   Integer tiempoPreparacion, Integer porciones) {
        Favorito favorito = new Favorito(usuarioId, spoonacularId, titulo, 
                                        imagen, tiempoPreparacion, porciones);
        return favoriteRepository.save(favorito);
    }

    /**
     * Elimina un favorito (verificando que pertenece al usuario)
     */
    public void eliminarFavorito(String id, String usuarioId) throws Exception {
        Favorito favorito = favoriteRepository.findById(id)
                .orElseThrow(() -> new Exception("Favorito no encontrado"));
        
        if (!favorito.getUsuarioId().equals(usuarioId)) {
            throw new Exception("No tienes permiso para eliminar este favorito");
        }

        favoriteRepository.deleteById(id);
    }
}
