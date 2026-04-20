package com.recipehub.service;

import com.recipehub.model.Usuario;
import com.recipehub.model.UsuarioDTO;
import com.recipehub.repository.RepositorioUsuario;
import com.recipehub.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ServicioAutenticacion
 * Servicio para autenticación: login, registro y validación de usuarios
 */
@Service
public class ServicioAutenticacion {

    @Autowired
    private RepositorioUsuario usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Registra un nuevo usuario
     */
    public String registrarUsuario(String nombre, String email, String password) throws Exception {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(email)) {
            throw new Exception("El email ya está registrado");
        }

        // Hashear contraseña
        String passwordHasheada = passwordEncoder.encode(password);

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombre, email, passwordHasheada);
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        // Generar token JWT
        return jwtTokenProvider.generateToken(usuarioGuardado.getId());
    }

    /**
     * Realiza login de un usuario
     */
    public String loginUsuario(String email, String password) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();

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
    public UsuarioDTO obtenerUsuarioPorId(String id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        
        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}
