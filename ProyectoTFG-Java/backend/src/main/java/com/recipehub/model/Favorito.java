package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo Favorito
 * Representa una receta guardada como favorita por un usuario
 */
@Document(collection = "favorites")
public class Favorito {
    
    @Id
    private String id;
    
    private String usuarioId; // Referencia al User
    
    private String spoonacularId; // ID de la receta en Spoonacular
    
    private String titulo;
    
    private String imagen;
    
    private Integer tiempoPreparacion;
    
    private Integer porciones;
    
    private String fuente; // "spoonacular" o "personal"
    
    private Long fechaGuardada;

    public Favorito() {}

    public Favorito(String usuarioId, String spoonacularId, String titulo, 
                    String imagen, Integer tiempoPreparacion, Integer porciones) {
        this.usuarioId = usuarioId;
        this.spoonacularId = spoonacularId;
        this.titulo = titulo;
        this.imagen = imagen;
        this.tiempoPreparacion = tiempoPreparacion;
        this.porciones = porciones;
        this.fuente = "spoonacular";
        this.fechaGuardada = System.currentTimeMillis();
    }

    public Favorito(String id, String usuarioId, String spoonacularId, String titulo,
                    String imagen, Integer tiempoPreparacion, Integer porciones,
                    String fuente, Long fechaGuardada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.spoonacularId = spoonacularId;
        this.titulo = titulo;
        this.imagen = imagen;
        this.tiempoPreparacion = tiempoPreparacion;
        this.porciones = porciones;
        this.fuente = fuente;
        this.fechaGuardada = fechaGuardada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getSpoonacularId() {
        return spoonacularId;
    }

    public void setSpoonacularId(String spoonacularId) {
        this.spoonacularId = spoonacularId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Long getFechaGuardada() {
        return fechaGuardada;
    }

    public void setFechaGuardada(Long fechaGuardada) {
        this.fechaGuardada = fechaGuardada;
    }
}
