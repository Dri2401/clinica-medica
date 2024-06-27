package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.model.RetornoModel;

@Repository
public interface RetornoRepository extends JpaRepository<RetornoModel, Long> {
    
}
