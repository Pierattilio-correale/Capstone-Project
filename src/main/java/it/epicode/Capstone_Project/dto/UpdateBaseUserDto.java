package it.epicode.Capstone_Project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateBaseUserDto {
    private String username;
    private String email;
    private LocalDate dataNascita;
}
