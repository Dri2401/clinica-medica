package br.edu.imepac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.Receituario;
@Repository
public interface ReceituarioRepository extends JpaRepository<Receituario, Long> {
    
}