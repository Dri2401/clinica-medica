package br.edu.imepac.models;

import org.hibernate.mapping.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "receituario")
public class Receituario {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)  
private Long Id;
private List medicamentos;
    
}
