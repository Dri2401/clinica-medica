package br.edu.imepac.dtos.paciente;

import lombok.Data;

@Data
public class PacienteDtoRequest {
    private String nome;
    private String cpf;
    private Long convenioId;
}
