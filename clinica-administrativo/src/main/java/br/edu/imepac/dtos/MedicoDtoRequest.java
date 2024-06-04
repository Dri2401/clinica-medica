package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class MedicoDtoRequest {
    private String nome;
    private String crm;
    private String senha;
}
