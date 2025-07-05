package it.epicode.Capstone_Project.repository;

import it.epicode.Capstone_Project.model.Storia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoriaRepository extends JpaRepository<Storia,Integer> {
    Optional<Storia> findByIdAndAutore_Username(int id, String username);
}
