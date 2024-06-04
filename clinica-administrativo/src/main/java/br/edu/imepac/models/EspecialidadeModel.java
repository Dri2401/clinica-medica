package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "especialidade")
public class EspecialidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
