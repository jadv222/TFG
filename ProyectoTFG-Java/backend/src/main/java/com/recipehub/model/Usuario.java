package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo Usuario
 * Representa un usuario en la base de datos MongoDB
 * Se usa para login, registro y autenticación
 */
@Document(collection = "users")
public class Usuario {
    
    @Id
    private String id;
    
    private String nombre;
    
    @Indexed(unique = true)
    private String email;
    
    private String password; // Será hasheada con BCrypt
    
    private String rol = "USER"; // ADMIN o USER
    
    private Long fechaRegistro;

    public Usuario() {}

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = "USER";
        this.fechaRegistro = System.currentTimeMillis();
    }

    public Usuario(String id, String nombre, String email, String password, Long fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = "USER";
        this.fechaRegistro = fechaRegistro;
    }
    
    public Usuario(String nombre, String email, String password, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.fechaRegistro = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
