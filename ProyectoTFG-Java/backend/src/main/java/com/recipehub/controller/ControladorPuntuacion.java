package com.recipehub.controller;

import com.recipehub.model.Puntuacion;
import com.recipehub.service.ServicioPuntuacion;
import com.recipehub.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ControladorPuntuacion
 * Endpoints para gestionar puntuaciones de recetas
 */
@RestController
@RequestMapping("/api/puntuaciones")
@CrossOrigin(origins = "http://localhost:8081")
public class ControladorPuntuacion {

    @Autowired
    private ServicioPuntuacion servicioPuntuacion;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * POST /api/puntuaciones
     * Crea o actualiza una puntuación (requiere autenticación)
     */
    @PostMapping
    public ResponseEntity<?> guardarPuntuacion(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody Map<String, ?> request) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token no proporcionado"));
            }
            
            String usuarioId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            String recetaId = (String) request.get("recetaId");
            Integer puntuacion = ((Number) request.get("puntuacion")).intValue();
            
            Puntuacion nuevaPuntuacion = servicioPuntuacion.guardarPuntuacion(recetaId, usuarioId, puntuacion);
            return ResponseEntity.ok(nuevaPuntuacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/puntuaciones/receta/{recetaId}
     * Obtiene el promedio y cantidad de puntuaciones de una receta
     */
    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<?> obtenerEstadisticasReceta(@PathVariable String recetaId) {
        try {
            Double promedio = servicioPuntuacion.obtenerPromedioPuntuaciones(recetaId);
            Long cantidad = servicioPuntuacion.obtenerCantidadPuntuaciones(recetaId);
            
            Map<String, Object> estadisticas = Map.of(
                    "recetaId", recetaId,
                    "promedio", promedio,
                    "cantidad", cantidad
            );
            
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/puntuaciones/receta/{recetaId}/usuario/{usuarioId}
     * Obtiene la puntuación de un usuario para una receta
     */
    @GetMapping("/receta/{recetaId}/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPuntuacionUsuario(
            @PathVariable String recetaId,
            @PathVariable String usuarioId) {
        try {
            Optional<Puntuacion> puntuacion = servicioPuntuacion.obtenerPuntuacionUsuario(recetaId, usuarioId);
            
            if (puntuacion.isPresent()) {
                return ResponseEntity.ok(puntuacion.get());
            } else {
                return ResponseEntity.ok(Map.of("puntuacion", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/puntuaciones/receta/{recetaId}
     * Elimina la puntuación de un usuario (requiere autenticación)
     */
    @DeleteMapping("/receta/{recetaId}")
    public ResponseEntity<?> eliminarPuntuacion(
            @PathVariable String recetaId,
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token no proporcionado"));
            }
            
            String usuarioId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            servicioPuntuacion.eliminarPuntuacion(recetaId, usuarioId);
            
            return ResponseEntity.ok(Map.of("mensaje", "Puntuación eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/puntuaciones/usuario/{usuarioId}
     * Obtiene todas las puntuaciones de un usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPuntuacionesUsuario(@PathVariable String usuarioId) {
        try {
            List<Puntuacion> puntuaciones = servicioPuntuacion.obtenerPuntuacionesUsuario(usuarioId);
            return ResponseEntity.ok(puntuaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
