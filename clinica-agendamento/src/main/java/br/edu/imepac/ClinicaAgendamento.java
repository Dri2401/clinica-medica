package br.edu.imepac;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Clinica-Agendamento Swagger", version = "1", description = 
"API desenvolvida para projeto acadêmico."))
public class ClinicaAgendamento {
    public static void main(String[] args) {
        SpringApplication.run(ClinicaAgendamento.class, args);
    }
}
