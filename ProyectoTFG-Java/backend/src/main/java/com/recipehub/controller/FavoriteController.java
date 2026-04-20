package com.recipehub.controller;

import com.recipehub.model.Favorite;
import com.recipehub.security.JwtTokenProvider;
import com.recipehub.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * FavoriteController
 * REST Endpoints para gestión de favoritos
 * (Requieren autenticación con JWT)
 * 
 * GET /api/favorites - Obtener favoritos del usuario
 * POST /api/favorites/guardar - Guardar nueva receta como favorita
 * DELETE /api/favorites/{id} - Eliminar un favorito
 */
@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Extrae el userId del token JWT en el header
     */
    private String obtenerUserIdDelToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtTokenProvider.getUserIdFromToken(token);
        }
        return null;
    }

    /**
     * GET /api/favorites
     * Obtiene todos los favoritos del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<?> obtenerFavoritos(HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            List<Favorite> favoritos = favoriteService.obtenerFavoritos(usuarioId);
            return ResponseEntity.ok(Map.of(
                "total", favoritos.size(),
                "favoritos", favoritos
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /**
     * POST /api/favorites/guardar
     * Guarda una receta como favorita
     */
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarFavorito(
            @RequestBody Map<String, Object> body,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            String titulo = (String) body.get("titulo");
            if (titulo == null || titulo.isEmpty()) {
                return ResponseEntity.badRequest().body("El título es requerido");
            }

            Favorite favorito = favoriteService.guardarFavorito(
                usuarioId,
                (String) body.getOrDefault("spoonacularId", ""),
                titulo,
                (String) body.getOrDefault("imagen", ""),
                (Integer) body.getOrDefault("tiempoPreparacion", 30),
                (Integer) body.getOrDefault("porciones", 4)
            );

            return ResponseEntity.ok(Map.of(
                "mensaje", "Receta guardada en favoritos",
                "favorito", favorito
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/favorites/{id}
     * Elimina una receta de favoritos
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFavorito(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            favoriteService.eliminarFavorito(id, usuarioId);

            return ResponseEntity.ok(Map.of(
                "mensaje", "Favorito eliminado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
