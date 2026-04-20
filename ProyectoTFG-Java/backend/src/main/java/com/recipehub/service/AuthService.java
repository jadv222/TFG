package com.recipehub.service;

import com.recipehub.model.User;
import com.recipehub.model.UserDTO;
import com.recipehub.repository.UserRepository;
import com.recipehub.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthService
 * Servicio para autenticación: login, registro y validación de usuarios
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Registra un nuevo usuario
     */
    public String registrarUsuario(String nombre, String email, String password) throws Exception {
        // Validar que el email no exista
        if (userRepository.existsByEmail(email)) {
            throw new Exception("El email ya está registrado");
        }

        // Hashear contraseña
        String passwordHasheada = passwordEncoder.encode(password);

        // Crear nuevo usuario
        User nuevoUsuario = new User(nombre, email, passwordHasheada);
        User usuarioGuardado = userRepository.save(nuevoUsuario);

        // Generar token JWT
        return jwtTokenProvider.generateToken(usuarioGuardado.getId());
    }

    /**
     * Realiza login de un usuario
     */
    public String loginUsuario(String email, String password) throws Exception {
        Optional<User> usuarioOpt = userRepository.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Credenciales inválidas");
        }

        User usuario = usuarioOpt.get();

        // Validar contraseña
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Credenciales inválidas");
        }

        // Generar token JWT
        return jwtTokenProvider.generateToken(usuario.getId());
    }

    /**
     * Obtiene datos del usuario por ID (sin contraseña)
     */
    public UserDTO obtenerUsuarioPorId(String id) throws Exception {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        
        return new UserDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
