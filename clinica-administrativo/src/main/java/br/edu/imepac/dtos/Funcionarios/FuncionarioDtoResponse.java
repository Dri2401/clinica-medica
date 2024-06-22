package br.edu.imepac.dtos.Funcionarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDtoResponse {
    private Long id;
    private String nome;
    private String cargo;
}
