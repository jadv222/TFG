package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo Comentario
 * Representa un comentario dejado por un usuario en una receta
 */
@Document(collection = "comentarios")
public class Comentario {
    
    @Id
    private String id;
    
    private String recetaId;
    private String usuarioId;
    private String usuarioNombre;
    private String contenido;
    private Long fechaCreacion;
    private Long fechaModificacion;

    public Comentario() {}

    public Comentario(String recetaId, String usuarioId, String usuarioNombre, String contenido) {
        this.recetaId = recetaId;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.contenido = contenido;
        this.fechaCreacion = System.currentTimeMillis();
        this.fechaModificacion = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(String recetaId) {
        this.recetaId = recetaId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Long fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
