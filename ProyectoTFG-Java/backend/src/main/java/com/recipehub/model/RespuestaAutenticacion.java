package com.recipehub.model;

/**
 * DTO (Data Transfer Object) para respuestas de autenticación
 * Usado para devolver token y datos del usuario
 */
public class RespuestaAutenticacion {
    private String token;
    private String mensaje;
    private UsuarioDTO usuario;

    public RespuestaAutenticacion() {}

    public RespuestaAutenticacion(String token, String mensaje, UsuarioDTO usuario) {
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
