package br.edu.imepac.dtos.Receituario;

import org.hibernate.mapping.List;

import lombok.Data;

@Data
public class ReceituarioDtoResponse {
    private Long Id;

    private List medicamentos;

    
}
