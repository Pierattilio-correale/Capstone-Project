package it.epicode.Capstone_Project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UserDto {
    @NotEmpty(message = "l' username non può essere nullo o vuoto!")
    private String username;
    @NotEmpty(message = "il nome non può essere nullo o vuoto!")
    private String nome;
    @NotEmpty(message = "il cognome non può essere nullo o vuoto!")
    private   String cognome;
    @Email(message = "l'email non può essere nulla o vuota!")
    private String email;
    @NotEmpty(message = "la password non può essere nulla o vuota!")
    private String password;
    @NotNull(message = "la data di nascità non può essere nulla o vuota!")
    private LocalDate dataNascita;
    private String descrizione;


}
