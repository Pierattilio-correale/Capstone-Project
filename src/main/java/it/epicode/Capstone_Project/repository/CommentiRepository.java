package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Commenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentiRepository extends JpaRepository<Commenti, Integer> {
    List<Commenti> findByCapitoloId(int capitoloId);
    @Query("SELECT COUNT(cm) FROM Commenti cm WHERE cm.capitolo.storia.autore.id = :autoreId")
    int countCommentiByAutoreId(@Param("autoreId") int autoreId);
}
