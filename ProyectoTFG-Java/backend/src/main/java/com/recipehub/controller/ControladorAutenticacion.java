package com.recipehub.controller;

import com.recipehub.model.*;
import com.recipehub.service.ServicioAutenticacion;
import com.recipehub.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ControladorAutenticacion
 * REST Endpoints para autenticación: login y registro
 * 
 * POST /api/auth/registro - Registrar nuevo usuario
 * POST /api/auth/login - Login de usuario
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControladorAutenticacion {

    @Autowired
    private ServicioAutenticacion authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * POST /api/auth/registro
     * Registra a un nuevo usuario
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registroUsuario(@RequestBody SolicitudRegistro signUpRequest) {
        try {
            if (signUpRequest.getNombre() == null || signUpRequest.getNombre().isEmpty() ||
                signUpRequest.getEmail() == null || signUpRequest.getEmail().isEmpty() ||
                signUpRequest.getPassword() == null || signUpRequest.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Todos los campos son requeridos");
            }

            String token = authService.registrarUsuario(
                signUpRequest.getNombre(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword()
            );

            String userId = jwtTokenProvider.getUserIdFromToken(token);
            UsuarioDTO usuario = authService.obtenerUsuarioPorId(userId);

            RespuestaAutenticacion response = new RespuestaAutenticacion(
                token,
                "Usuario registrado correctamente",
                usuario
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * POST /api/auth/login
     * Login de usuario existente
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SolicitudLogin loginRequest) {
        try {
            if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email y contraseña requeridos");
            }

            String token = authService.loginUsuario(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            );

            String userId = jwtTokenProvider.getUserIdFromToken(token);
            UsuarioDTO usuario = authService.obtenerUsuarioPorId(userId);

            RespuestaAutenticacion response = new RespuestaAutenticacion(
                token,
                "Login exitoso",
                usuario
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
