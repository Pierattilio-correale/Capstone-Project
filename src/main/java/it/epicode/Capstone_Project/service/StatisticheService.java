package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.StatisticheDto;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StatisticheService {

    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private CapitoloRepository capitoloRepository;

    @Autowired
    private CommentiRepository commentiRepository;

    @Autowired
    private UserRepository userRepository;

    public StatisticheDto getStatistichePersonali() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        int storie = storiaRepository.findByAutore_Username(username).size();


        int capitoli = capitoloRepository.countByStoria_Autore_Username(username);


        int commenti = commentiRepository.countByCapitolo_Storia_Autore_Username(username);


        double media = (storie > 0) ? (double) capitoli / storie : 0.0;

        StatisticheDto dto = new StatisticheDto();
        dto.setNumeroStorie(storie);
        dto.setNumeroCapitoli(capitoli);
        dto.setNumeroCommenti(commenti);
        dto.setMediaCapitoliPerStoria(media);

        return dto;
    }
    public StatisticheDto getStatisticheGlobali() {
        int storie = (int) storiaRepository.count();   // conta tutte le storie
        int capitoli = (int) capitoloRepository.count(); // conta tutti i capitoli
        int commenti = (int) commentiRepository.count(); // conta tutti i commenti

        double media = (storie > 0) ? (double) capitoli / storie : 0.0;

        StatisticheDto dto = new StatisticheDto();
        dto.setNumeroStorie(storie);
        dto.setNumeroCapitoli(capitoli);
        dto.setNumeroCommenti(commenti);
        dto.setMediaCapitoliPerStoria(media);

        return dto;
    }
}