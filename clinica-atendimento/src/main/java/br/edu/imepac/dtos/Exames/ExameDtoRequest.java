package br.edu.imepac.dtos.Exames;

import lombok.Data;

@Data
public class ExameDtoRequest {
    
    private String tipoExame;

    private Long medicoSolicitante;
}
