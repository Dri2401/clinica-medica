package br.edu.imepac.dtos.paciente;

import lombok.Data;

@Data
public class PacienteDtoResponse {
    private Long id;
    private String nome;
    private Long convenioId;
}
