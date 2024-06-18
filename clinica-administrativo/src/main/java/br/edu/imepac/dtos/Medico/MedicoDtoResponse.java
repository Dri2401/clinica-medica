package br.edu.imepac.dtos.Medico;


import lombok.Data;

@Data
public class MedicoDtoResponse {
    private Long id;
    private String nome;
    private String crm;
    private String especialidadeNome;
}
