package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoriaRepository extends JpaRepository<Storia,Integer> {
    Optional<Storia> findByIdAndAutore_Username(int id, String username);
    List<Storia> findByTitoloContainingIgnoreCase(String titolo);
    List<Storia> findByAutore_Id(int autoreId);
}
