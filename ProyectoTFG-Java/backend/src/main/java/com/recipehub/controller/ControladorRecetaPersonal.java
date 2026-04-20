package com.recipehub.controller;

import com.recipehub.model.RecetaPersonal;
import com.recipehub.security.JwtTokenProvider;
import com.recipehub.service.ServicioRecetaPersonal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ControladorRecetaPersonal
 * REST Endpoints para gestión de recetas personales del usuario
 * (Requieren autenticación con JWT)
 * 
 * GET /api/mis-recetas - Obtener recetas del usuario
 * POST /api/mis-recetas - Crear nueva receta
 * GET /api/mis-recetas/{id} - Obtener detalle de una receta
 * PUT /api/mis-recetas/{id} - Actualizar una receta
 * DELETE /api/mis-recetas/{id} - Eliminar una receta
 */
@RestController
@RequestMapping("/api/mis-recetas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControladorRecetaPersonal {

    @Autowired
    private ServicioRecetaPersonal recetaService;

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
     * GET /api/mis-recetas
     * Obtiene todas las recetas del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<?> obtenerMisRecetas(HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            List<RecetaPersonal> recetas = recetaService.obtenerRecetasDelUsuario(usuarioId);
            return ResponseEntity.ok(Map.of(
                "total", recetas.size(),
                "recetas", recetas
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET /api/mis-recetas/{id}
     * Obtiene una receta específica del usuario
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMiReceta(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            RecetaPersonal receta = recetaService.obtenerRecetaDelUsuario(id, usuarioId);
            return ResponseEntity.ok(receta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * POST /api/mis-recetas
     * Crea una nueva receta personal
     */
    @PostMapping
    public ResponseEntity<?> crearReceta(
            @RequestBody RecetaPersonal recetaData,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            RecetaPersonal receta = recetaService.crearReceta(usuarioId, recetaData);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Receta creada exitosamente",
                "receta", receta
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * PUT /api/mis-recetas/{id}
     * Actualiza una receta personal
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReceta(
            @PathVariable String id,
            @RequestBody RecetaPersonal recetaData,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            RecetaPersonal receta = recetaService.actualizarReceta(id, usuarioId, recetaData);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Receta actualizada exitosamente",
                "receta", receta
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/mis-recetas/{id}
     * Elimina una receta personal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReceta(
            @PathVariable String id,
            HttpServletRequest request) {
        try {
            String usuarioId = obtenerUserIdDelToken(request);
            if (usuarioId == null) {
                return ResponseEntity.status(401).body("No autorizado");
            }

            recetaService.eliminarReceta(id, usuarioId);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Receta eliminada correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
