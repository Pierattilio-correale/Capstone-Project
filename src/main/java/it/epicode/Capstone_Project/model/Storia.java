package it.epicode.Capstone_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Storia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titolo;
    private String descrizione;
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;


    @OneToMany(mappedBy = "storia")
    private List<Capitolo>capitoli;

    @ManyToOne
    @JoinColumn(name = "autore_id")
    private User autore;

}
