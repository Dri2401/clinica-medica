package br.edu.imepac.dtos.Funcionarios;

import lombok.Data;

@Data
public class FuncionarioDtoResponse {
    private Long id;
    private String nome;
    private String cargo;
}
