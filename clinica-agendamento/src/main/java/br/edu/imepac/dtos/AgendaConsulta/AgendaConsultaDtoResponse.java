package br.edu.imepac.dtos.AgendaConsulta;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
@Data
public class AgendaConsultaDtoResponse {

private Long Id;
private String nomePaciente;
private LocalDate dataConsulta;
private String medicoNome;
private LocalTime horario;

}
