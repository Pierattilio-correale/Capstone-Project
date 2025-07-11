package it.epicode.Capstone_Project.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentiDto {

@NotEmpty(message = "il contenuto non può essere nullo o vuoto")
    private String contenuto;

    @Min(value = 1, message = "Il voto minimo è 1")
    @Max(value = 5, message = "Il voto massimo è 5")
    private int voto;

    @NotNull(message = "un commento deve essere riferito ad un capitolo")
    private Integer capitoloId;

    @NotNull(message = "un commento deve essere fatto da un utente")
    private Integer userId;


}

