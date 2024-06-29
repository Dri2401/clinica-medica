package br.edu.imepac;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Clinica-Administrativo Swagger", version = "1", description = 
"API desenvolvida para projeto acadêmico."))
public class ClinicaAdministrativo {
    public static void main(String[] args) {
        SpringApplication.run(ClinicaAdministrativo.class, args);
    }
}
