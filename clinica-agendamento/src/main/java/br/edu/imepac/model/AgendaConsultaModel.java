package br.edu.imepac.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "AgendaConsulta")
@Entity

public class AgendaConsultaModel {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)   
private Long id;
private String nomePaciente;
private LocalDate dataConsulta;
private Long medicoID;
private LocalTime horario;

}
