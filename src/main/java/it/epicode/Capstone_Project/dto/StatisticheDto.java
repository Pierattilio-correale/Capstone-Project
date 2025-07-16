package it.epicode.Capstone_Project.dto;

import lombok.Data;

@Data
public class StatisticheDto {
    private int numeroStorie;
    private int numeroCapitoli;
    private int numeroCommenti;
    private double mediaCapitoliPerStoria;
}