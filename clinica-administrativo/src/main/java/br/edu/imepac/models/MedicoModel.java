package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicos")
public class MedicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private EspecialidadeModel especialidade;
}
