package com.recipehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ServicioReceta
 * Servicio para gestionar recetas
 * - Búsqueda a través de Spoonacular API
 * - Obtención de detalles de recetas
 */
@Service
public class ServicioReceta {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spoonacular.api.key}")
    private String apiKey;

    @Value("${spoonacular.api.url}")
    private String apiBaseUrl;

    /**
     * Busca recetas en Spoonacular
     */
    public Object buscarRecetas(String query) {
        String url = String.format(
            "%s/recipes/complexSearch?query=%s&apiKey=%s&addRecipeInformation=true&fillIngredients=true&number=12",
            apiBaseUrl, query, apiKey
        );
        
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error buscando recetas: " + e.getMessage());
        }
    }

    /**
     * Obtiene detalles completos de una receta
     */
    public Object obtenerDetalleReceta(int recetaId) {
        String url = String.format(
            "%s/recipes/%d/information?apiKey=%s&includeNutrition=true",
            apiBaseUrl, recetaId, apiKey
        );
        
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo detalle: " + e.getMessage());
        }
    }
}
