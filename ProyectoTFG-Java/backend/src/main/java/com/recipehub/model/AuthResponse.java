package com.recipehub.model;

/**
 * DTO (Data Transfer Object) para respuestas de autenticación
 * Usado para devolver token y datos del usuario
 */
public class AuthResponse {
    private String token;
    private String mensaje;
    private UserDTO usuario;

    public AuthResponse() {}

    public AuthResponse(String token, String mensaje, UserDTO usuario) {
        this.token = token;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public UserDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDTO usuario) {
        this.usuario = usuario;
    }
}
