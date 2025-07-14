package it.epicode.Capstone_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Capitolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)

    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String contenuto;

    @Column(name = "numero_capitolo")
    private int numeroCapitolo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "storia_id")
    private Storia storia;

    @OneToMany(mappedBy = "capitolo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Commenti> commenti = new ArrayList<>();
}
