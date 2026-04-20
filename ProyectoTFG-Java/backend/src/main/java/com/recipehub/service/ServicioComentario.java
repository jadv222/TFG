package com.recipehub.service;

import com.recipehub.model.Comentario;
import com.recipehub.model.Usuario;
import com.recipehub.repository.RepositorioComentario;
import com.recipehub.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ServicioComentario
 * Servicio para gestionar comentarios en recetas
 */
@Service
public class ServicioComentario {

    @Autowired
    private RepositorioComentario repositorioComentario;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    /**
     * Crea un nuevo comentario
     */
    public Comentario crearComentario(String recetaId, String usuarioId, String contenido) throws Exception {
        // Validar que el usuario exista
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        
        // Validar que el contenido no esté vacío
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new Exception("El comentario no puede estar vacío");
        }
        
        if (contenido.length() > 1000) {
            throw new Exception("El comentario no puede exceder 1000 caracteres");
        }
        
        Comentario comentario = new Comentario(recetaId, usuarioId, usuario.getNombre(), contenido);
        return repositorioComentario.save(comentario);
    }

    /**
     * Obtiene todos los comentarios de una receta
     */
    public List<Comentario> obtenerComentariosReceta(String recetaId) {
        return repositorioComentario.findByRecetaIdOrderByFechaCreacionDesc(recetaId);
    }

    /**
     * Obtiene la cantidad de comentarios de una receta
     */
    public Long obtenerCantidadComentarios(String recetaId) {
        List<Comentario> comentarios = repositorioComentario.findByRecetaIdOrderByFechaCreacionDesc(recetaId);
        return (long) comentarios.size();
    }

    /**
     * Actualiza un comentario (solo si es del usuario o es admin)
     */
    public Comentario actualizarComentario(String comentarioId, String usuarioId, String nuevoContenido, String rolUsuario) throws Exception {
        Comentario comentario = repositorioComentario.findById(comentarioId)
                .orElseThrow(() -> new Exception("Comentario no encontrado"));
        
        // Validar que sea el dueño o admin
        if (!comentario.getUsuarioId().equals(usuarioId) && !"ADMIN".equals(rolUsuario)) {
            throw new Exception("No tienes permiso para editar este comentario");
        }
        
        // Validar contenido
        if (nuevoContenido == null || nuevoContenido.trim().isEmpty()) {
            throw new Exception("El comentario no puede estar vacío");
        }
        
        if (nuevoContenido.length() > 1000) {
            throw new Exception("El comentario no puede exceder 1000 caracteres");
        }
        
        comentario.setContenido(nuevoContenido);
        comentario.setFechaModificacion(System.currentTimeMillis());
        return repositorioComentario.save(comentario);
    }

    /**
     * Elimina un comentario (solo si es del usuario o es admin)
     */
    public void eliminarComentario(String comentarioId, String usuarioId, String rolUsuario) throws Exception {
        Comentario comentario = repositorioComentario.findById(comentarioId)
                .orElseThrow(() -> new Exception("Comentario no encontrado"));
        
        // Validar que sea el dueño o admin
        if (!comentario.getUsuarioId().equals(usuarioId) && !"ADMIN".equals(rolUsuario)) {
            throw new Exception("No tienes permiso para eliminar este comentario");
        }
        
        repositorioComentario.deleteById(comentarioId);
    }

    /**
     * Obtiene todos los comentarios de un usuario
     */
    public List<Comentario> obtenerComentariosUsuario(String usuarioId) {
        return repositorioComentario.findByUsuarioId(usuarioId);
    }

    /**
     * Elimina todos los comentarios de una receta (usado cuando se elimina la receta)
     */
    public void eliminarComentariosReceta(String recetaId) {
        repositorioComentario.deleteByRecetaId(recetaId);
    }
}
