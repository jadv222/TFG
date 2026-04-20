package com.recipehub.config;

import com.recipehub.model.Usuario;
import com.recipehub.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer
 * Inicializa datos por defecto en la aplicación (p.ej. usuario admin)
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RepositorioUsuario usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${app.admin.email:admin@recetario.com}")
    private String adminEmail;
    
    @Value("${app.admin.password:Admin123!}")
    private String adminPassword;
    
    @Value("${app.admin.nombre:Administrador}")
    private String adminNombre;

    /**
     * Se ejecuta cuando la aplicación inicia
     */
    @Override
    public void run(String... args) throws Exception {
        // Verificar si el admin ya existe
        if (!usuarioRepository.existsByEmail(adminEmail)) {
            System.out.println("🔐 Creando usuario admin...");
            
            Usuario admin = new Usuario(adminNombre, adminEmail, passwordEncoder.encode(adminPassword), "ADMIN");
            usuarioRepository.save(admin);
            
            System.out.println("✅ Usuario admin creado correctamente");
            System.out.println("   📧 Email: " + adminEmail);
            System.out.println("   🔑 Contraseña: " + adminPassword);
        } else {
            System.out.println("ℹ️ El usuario admin ya existe");
        }
    }
}
