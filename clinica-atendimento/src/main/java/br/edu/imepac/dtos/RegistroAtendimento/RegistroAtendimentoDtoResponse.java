package br.edu.imepac.dtos.RegistroAtendimento;

import lombok.Data;

@Data
public class RegistroAtendimentoDtoResponse {
    private Long Id;

    private Long pacienteId;

    private String descricao;
    
}
