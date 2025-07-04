package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Capitolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CapitoloRepository extends JpaRepository<Capitolo,Integer> {
    Optional<Capitolo> findByIdAndStoria_Autore_Username(int id, String username);
}
