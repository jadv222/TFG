package com.recipehub.controller;

import com.recipehub.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RecipeController
 * REST Endpoints para gestión de recetas
 * 
 * GET /api/recipes/buscar?query=pasta - Buscar recetas
 * GET /api/recipes/detalle/{id} - Obtener detalles de una receta
 */
@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    /**
     * GET /api/recipes/buscar?query=pasta
     * Busca recetas en Spoonacular API
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarRecetas(@RequestParam String query) {
        try {
            if (query == null || query.isEmpty()) {
                return ResponseEntity.badRequest().body("Se requiere un término de búsqueda");
            }

            Object recetas = recipeService.buscarRecetas(query);
            return ResponseEntity.ok(recetas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /**
     * GET /api/recipes/detalle/{id}
     * Obtiene los detalles completos de una receta
     */
    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleReceta(@PathVariable int id) {
        try {
            Object detalle = recipeService.obtenerDetalleReceta(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
