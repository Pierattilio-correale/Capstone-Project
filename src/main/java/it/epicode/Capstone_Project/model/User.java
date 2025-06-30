package it.epicode.Capstone_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.Capstone_Project.enumerated.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    private String password;
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "autore")
    private List<Storia>storie;

}
