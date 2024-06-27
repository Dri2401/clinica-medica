package br.edu.imepac.dtos.Retorno;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
@Data
public class RetornoDtoResponse {
    
    private Long id;
    
    private String medicoNome;

    private String nomePaciente;

    private LocalDate dataRetorno;

    private LocalTime horario;

}
