package br.edu.imepac.dtos.AgendaConsulta;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
@Data
public class AgendaConsultaDtoRequest {
    
private String nomePaciente;
private LocalDate dataConsulta;
private Long medicoID;
private LocalTime horario;

}
