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
    @NotEmpty(message = "la descrizione della storia non può essere nulla o vuota")
    private String descrizione;
    private int userId;
    private List<Integer> capitoloId;
}
