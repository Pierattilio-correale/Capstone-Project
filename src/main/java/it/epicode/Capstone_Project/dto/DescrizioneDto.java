package it.epicode.Capstone_Project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DescrizioneDto {
    @NotBlank(message = "La descrizione non può essere vuota")
    private String descrizione;
}