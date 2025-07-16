package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Commenti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentiRepository extends JpaRepository<Commenti, Integer> {
    List<Commenti> findByCapitoloId(int capitoloId);
    int countByCapitolo_Storia_Autore_Username(String username);
}
