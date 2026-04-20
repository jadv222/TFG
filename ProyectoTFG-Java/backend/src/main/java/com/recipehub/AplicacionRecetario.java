package com.recipehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * AplicacionRecetario
 * Clase principal que inicia la aplicación Spring Boot
 */
@SpringBootApplication
public class AplicacionRecetario {

    public static void main(String[] args) {
        SpringApplication.run(AplicacionRecetario.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
