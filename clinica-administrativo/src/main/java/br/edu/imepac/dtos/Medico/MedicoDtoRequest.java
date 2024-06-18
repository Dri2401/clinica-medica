package br.edu.imepac.dtos.Medico;

import lombok.Data;

@Data
public class MedicoDtoRequest {
    private String nome;
    private String crm;
    private String senha;
    private Long especialidadeId;
}
