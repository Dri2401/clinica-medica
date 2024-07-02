package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.RegistroAtendimento;
@Repository
public interface RegistroAtendimentoRepository extends JpaRepository<RegistroAtendimento, Long>{
    
}
