package com.recipehub.service;

import com.recipehub.model.Favorite;
import com.recipehub.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FavoriteService
 * Servicio para gestionar recetas favoritas
 */
@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    /**
     * Obtiene todos los favoritos de un usuario
     */
    public List<Favorite> obtenerFavoritos(String usuarioId) {
        return favoriteRepository.findByUsuarioIdOrderByFechaGuardadaDesc(usuarioId);
    }

    /**
     * Guarda una receta como favorita
     */
    public Favorite guardarFavorito(String usuarioId, String spoonacularId, 
                                   String titulo, String imagen, 
                                   Integer tiempoPreparacion, Integer porciones) {
        Favorite favorito = new Favorite(usuarioId, spoonacularId, titulo, 
                                        imagen, tiempoPreparacion, porciones);
        return favoriteRepository.save(favorito);
    }

    /**
     * Elimina un favorito (verificando que pertenece al usuario)
     */
    public void eliminarFavorito(String id, String usuarioId) throws Exception {
        Favorite favorito = favoriteRepository.findById(id)
                .orElseThrow(() -> new Exception("Favorito no encontrado"));
        
        if (!favorito.getUsuarioId().equals(usuarioId)) {
            throw new Exception("No tienes permiso para eliminar este favorito");
        }

        favoriteRepository.deleteById(id);
    }
}
