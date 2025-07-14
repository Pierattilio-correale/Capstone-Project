package it.epicode.Capstone_Project.dto;

import it.epicode.Capstone_Project.model.Capitolo;
import it.epicode.Capstone_Project.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class StoriaDto {
    @NotEmpty(message = "il titolo della storia non può essere nullo o vuoto")
    private String titolo;

    private String descrizione;
    @NotEmpty(message = "il genere della storia non può essere nulla o vuota")
    private String genere;
    private int userId;
    private List<Integer> capitoloId;
}
