package br.edu.imepac.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "exames")
public class Exames {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long Id;
    
private String tipoExame;

private String medicoSolicitante;


}
