package br.edu.imepac.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.imepac.models.Receituario;
@Repository
public interface ReceituarioRepository extends JpaRepository<Receituario, Long> {
 
}