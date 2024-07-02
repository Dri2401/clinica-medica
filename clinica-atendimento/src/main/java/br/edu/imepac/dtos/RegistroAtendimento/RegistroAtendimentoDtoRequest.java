package br.edu.imepac.dtos.RegistroAtendimento;

import lombok.Data;

@Data
public class RegistroAtendimentoDtoRequest {
    private Long pacienteId;

    private String descricao;
    
}
