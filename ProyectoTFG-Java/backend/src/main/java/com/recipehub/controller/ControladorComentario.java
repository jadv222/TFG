package com.recipehub.controller;

import com.recipehub.model.Comentario;
import com.recipehub.service.ServicioComentario;
import com.recipehub.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ControladorComentario
 * Endpoints para gestionar comentarios en recetas
 */
@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://localhost:8081")
public class ControladorComentario {

    @Autowired
    private ServicioComentario servicioComentario;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * GET /api/comentarios/receta/{recetaId}
     * Obtiene todos los comentarios de una receta
     */
    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<?> obtenerComentarios(@PathVariable String recetaId) {
        try {
            List<Comentario> comentarios = servicioComentario.obtenerComentariosReceta(recetaId);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/comentarios
     * Crea un nuevo comentario (requiere autenticación)
     */
    @PostMapping
    public ResponseEntity<?> crearComentario(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody Map<String, String> request) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token no proporcionado"));
            }
            
            String usuarioId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            String recetaId = request.get("recetaId");
            String contenido = request.get("contenido");
            
            Comentario comentario = servicioComentario.crearComentario(recetaId, usuarioId, contenido);
            return ResponseEntity.ok(comentario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/comentarios/{comentarioId}
     * Actualiza un comentario (requiere autenticación)
     */
    @PutMapping("/{comentarioId}")
    public ResponseEntity<?> actualizarComentario(
            @PathVariable String comentarioId,
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody Map<String, String> request) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token no proporcionado"));
            }
            
            String usuarioId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            String rolUsuario = jwtTokenProvider.getRoleFromToken(token.replace("Bearer ", ""));
            String nuevoContenido = request.get("contenido");
            
            Comentario comentario = servicioComentario.actualizarComentario(comentarioId, usuarioId, nuevoContenido, rolUsuario);
            return ResponseEntity.ok(comentario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/comentarios/{comentarioId}
     * Elimina un comentario (requiere autenticación)
     */
    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<?> eliminarComentario(
            @PathVariable String comentarioId,
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token no proporcionado"));
            }
            
            String usuarioId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
            String rolUsuario = jwtTokenProvider.getRoleFromToken(token.replace("Bearer ", ""));
            
            servicioComentario.eliminarComentario(comentarioId, usuarioId, rolUsuario);
            return ResponseEntity.ok(Map.of("mensaje", "Comentario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/comentarios/usuario/{usuarioId}
     * Obtiene todos los comentarios de un usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerComentariosUsuario(@PathVariable String usuarioId) {
        try {
            List<Comentario> comentarios = servicioComentario.obtenerComentariosUsuario(usuarioId);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
