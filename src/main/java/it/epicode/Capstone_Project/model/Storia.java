package it.epicode.Capstone_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String genere;
    private String descrizione;
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    @Column(name = "immagine_copertina")
private String immagineCopertina;

    @OneToMany(
            mappedBy = "storia",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Capitolo>capitoli;

    @ManyToOne
    @JoinColumn(name = "autore_id")
    @JsonIgnoreProperties("storie")
    private User autore;

}
