package br.edu.imepac.dtos.Exames;

import lombok.Data;

@Data
public class ExamesDtoResponse {
    private Long Id;
    
    private String tipoExame;
    
    private String medicoSolicitante;    
}
