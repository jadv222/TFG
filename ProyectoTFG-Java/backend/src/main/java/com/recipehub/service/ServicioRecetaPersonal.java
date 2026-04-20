package com.recipehub.service;

import com.recipehub.model.RecetaPersonal;
import com.recipehub.repository.RepositorioRecetaPersonal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ServicioRecetaPersonal
 * Servicio para gestionar recetas personales de los usuarios
 */
@Service
public class ServicioRecetaPersonal {

    @Autowired
    private RepositorioRecetaPersonal recetaRepository;

    /**
     * Obtiene todas las recetas personales de un usuario
     */
    public List<RecetaPersonal> obtenerRecetasDelUsuario(String usuarioId) {
        return recetaRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    /**
     * Obtiene una receta específica verificando que pertenece al usuario
     */
    public RecetaPersonal obtenerRecetaDelUsuario(String recetaId, String usuarioId) throws Exception {
        RecetaPersonal receta = recetaRepository.findByIdAndUsuarioId(recetaId, usuarioId);
        if (receta == null) {
            throw new Exception("Receta no encontrada o no tienes permiso para acceder a ella");
        }
        return receta;
    }

    /**
     * Crea una nueva receta personal
     */
    public RecetaPersonal crearReceta(String usuarioId, RecetaPersonal recetaData) throws Exception {
        if (recetaData.getTitulo() == null || recetaData.getTitulo().isEmpty()) {
            throw new Exception("El título de la receta es obligatorio");
        }

        RecetaPersonal receta = new RecetaPersonal(
            usuarioId,
            recetaData.getTitulo(),
            recetaData.getDescripcion(),
            recetaData.getTiempoPreparacion(),
            recetaData.getPorciones()
        );

        receta.setImagen(recetaData.getImagen());
        receta.setIngredientes(recetaData.getIngredientes());
        receta.setPasos(recetaData.getPasos());
        receta.setDificultad(recetaData.getDificultad());
        receta.setCategoría(recetaData.getCategoría());

        return recetaRepository.save(receta);
    }

    /**
     * Actualiza una receta personal
     */
    public RecetaPersonal actualizarReceta(String recetaId, String usuarioId, RecetaPersonal recetaData) throws Exception {
        RecetaPersonal receta = obtenerRecetaDelUsuario(recetaId, usuarioId);

        if (recetaData.getTitulo() != null) {
            receta.setTitulo(recetaData.getTitulo());
        }
        if (recetaData.getDescripcion() != null) {
            receta.setDescripcion(recetaData.getDescripcion());
        }
        if (recetaData.getImagen() != null) {
            receta.setImagen(recetaData.getImagen());
        }
        if (recetaData.getTiempoPreparacion() != null) {
            receta.setTiempoPreparacion(recetaData.getTiempoPreparacion());
        }
        if (recetaData.getPorciones() != null) {
            receta.setPorciones(recetaData.getPorciones());
        }
        if (recetaData.getIngredientes() != null) {
            receta.setIngredientes(recetaData.getIngredientes());
        }
        if (recetaData.getPasos() != null) {
            receta.setPasos(recetaData.getPasos());
        }
        if (recetaData.getDificultad() != null) {
            receta.setDificultad(recetaData.getDificultad());
        }
        if (recetaData.getCategoría() != null) {
            receta.setCategoría(recetaData.getCategoría());
        }

        receta.setFechaModificacion(System.currentTimeMillis());
        return recetaRepository.save(receta);
    }

    /**
     * Elimina una receta personal
     */
    public void eliminarReceta(String recetaId, String usuarioId) throws Exception {
        RecetaPersonal receta = obtenerRecetaDelUsuario(recetaId, usuarioId);
        recetaRepository.deleteByIdAndUsuarioId(recetaId, usuarioId);
    }
}
