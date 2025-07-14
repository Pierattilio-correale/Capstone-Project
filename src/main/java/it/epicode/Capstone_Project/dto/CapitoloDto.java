package it.epicode.Capstone_Project.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CapitoloDto {
    @NotEmpty(message = "il titolo non può essere nullo o vuoto!")
    private String titolo;
   
    private String contenuto;
    @NotNull(message = "il numero del capitolo non può essere nullo o vuoto!")
    private Integer numeroCapitolo;
    @NotNull
    private int storiaId;
}
