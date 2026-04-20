package com.recipehub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Modelo RecetaPersonal
 * Representa una receta creada personalmente por un usuario
 */
@Document(collection = "recetas_personales")
public class RecetaPersonal {
    
    @Id
    private String id;
    
    private String usuarioId; // ID del usuario propietario
    
    private String titulo;
    
    private String descripcion;
    
    private String imagen; // URL o base64 de la imagen
    
    private Integer tiempoPreparacion; // en minutos
    
    private Integer porciones;
    
    private List<String> ingredientes; // Lista de ingredientes
    
    private List<String> pasos; // Pasos para preparar
    
    private String dificultad; // Fácil, Medio, Difícil
    
    private String categoría; // Plato principal, postre, aperitivo, etc.
    
    private Long fechaCreacion;
    
    private Long fechaModificacion;

    public RecetaPersonal() {}

    public RecetaPersonal(String usuarioId, String titulo, String descripcion, 
                         Integer tiempoPreparacion, Integer porciones) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoPreparacion = tiempoPreparacion;
        this.porciones = porciones;
        this.fechaCreacion = System.currentTimeMillis();
        this.fechaModificacion = System.currentTimeMillis();
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
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
