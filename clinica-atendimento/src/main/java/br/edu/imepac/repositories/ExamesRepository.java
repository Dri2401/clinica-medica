package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.Exames;
@Repository
public interface ExamesRepository extends JpaRepository<Exames, Long> {
    
}
