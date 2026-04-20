package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo Puntuacion
 * Representa la puntuación que un usuario da a una receta
 */
@Document(collection = "puntuaciones")
public class Puntuacion {
    
    @Id
    private String id;
    
    private String recetaId;
    private String usuarioId;
    private Integer puntuacion; // 1-5
    private Long fechaCreacion;
    private Long fechaModificacion;

    public Puntuacion() {}

    public Puntuacion(String recetaId, String usuarioId, Integer puntuacion) {
        this.recetaId = recetaId;
        this.usuarioId = usuarioId;
        this.puntuacion = puntuacion;
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

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
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
