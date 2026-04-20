package com.recipehub.model;

/**
 * DTO (Data Transfer Object) para datos del usuario
 * No incluye la contraseña por seguridad
 */
public class UserDTO {
    private String id;
    private String nombre;
    private String email;

    public UserDTO() {}

    public UserDTO(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
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
}
