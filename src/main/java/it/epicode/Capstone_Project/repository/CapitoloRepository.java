package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Capitolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CapitoloRepository extends JpaRepository<Capitolo,Integer> {
    Optional<Capitolo> findByIdAndStoria_Autore_Username(int id, String username);
    @Query("SELECT COUNT(c) FROM Capitolo c WHERE c.storia.autore.id = :autoreId")
    int countCapitoliByAutoreId(@Param("autoreId") int autoreId);
}
