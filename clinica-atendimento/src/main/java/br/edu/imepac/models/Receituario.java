package br.edu.imepac.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Collate;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "receituario")
public class Receituario {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)  
private Long Id;
@ElementCollection
@CollectionTable(name= "receituario_medicamentos",joinColumns = @JoinColumn(name= "receituario_id"))
@Column(name= "medicamentos")
private List<String> medicamentos = new ArrayList<>();
    
}
