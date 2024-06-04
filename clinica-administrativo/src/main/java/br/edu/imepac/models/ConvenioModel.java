package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "convenios")
public class ConvenioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
