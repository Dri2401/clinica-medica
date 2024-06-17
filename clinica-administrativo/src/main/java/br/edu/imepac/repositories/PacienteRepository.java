package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.PacienteModel;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long>{
    
}
