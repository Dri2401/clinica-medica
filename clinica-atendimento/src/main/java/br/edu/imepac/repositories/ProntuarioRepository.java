package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.Prontuario;
@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    
}
