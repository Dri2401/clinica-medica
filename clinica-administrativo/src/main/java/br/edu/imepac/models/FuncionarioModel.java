package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ValueGenerationType;

@Data
@Entity
@Table(name = "funcionarios")
public class FuncionarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cargo;
}
