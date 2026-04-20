package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo User
 * Representa un usuario en la base de datos MongoDB
 * Se usa para login, registro y autenticación
 */
@Document(collection = "users")
public class User {
    
    @Id
    private String id;
    
    private String nombre;
    
    @Indexed(unique = true)
    private String email;
    
    private String password; // Será hasheada con BCrypt
    
    private Long fechaRegistro;

    public User() {}

    public User(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaRegistro = System.currentTimeMillis();
    }

    public User(String id, String nombre, String email, String password, Long fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
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

    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
