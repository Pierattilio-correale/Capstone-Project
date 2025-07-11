package it.epicode.Capstone_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Commenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "TEXT")
    private String contenuto;
            private int voto;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User autore;

    @ManyToOne
    @JoinColumn(name = "capitolo_id")

    private Capitolo capitolo;
}
