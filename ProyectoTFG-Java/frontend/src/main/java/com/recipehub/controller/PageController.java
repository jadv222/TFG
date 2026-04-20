package com.recipehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * PageController
 * Controlador para las páginas principales del frontend
 */
@Controller
public class PageController {

    /**
     * GET / - Página principal
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * GET /buscar - Página de búsqueda
     */
    @GetMapping("/buscar")
    public String buscar() {
        return "buscar";
    }

    /**
     * GET /crear - Página para crear receta
     */
    @GetMapping("/crear")
    public String crear() {
        return "crear";
    }

    /**
     * GET /mis-recetas - Página de mis recetas personales
     */
    @GetMapping("/mis-recetas")
    public String misRecetas() {
        return "mis-recetas";
    }

    /**
     * GET /favoritos - Página de favoritos
     */
    @GetMapping("/favoritos")
    public String favoritos() {
        return "favoritos";
    }

    /**
     * GET /receta/{id} - Página de detalle de receta
     */
    @GetMapping("/receta/{id}")
    public String detalleReceta(@PathVariable int id) {
        return "detalle-receta";
    }
}
