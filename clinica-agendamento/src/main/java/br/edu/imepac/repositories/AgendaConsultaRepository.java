package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.model.AgendaConsultaModel;
@Repository
public interface AgendaConsultaRepository extends JpaRepository <AgendaConsultaModel, Long> {

    
} 

