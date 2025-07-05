package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.CapitoloDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.UnauthorizedException;
import it.epicode.Capstone_Project.model.Capitolo;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.repository.CapitoloRepository;
import it.epicode.Capstone_Project.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapitoloService {

    @Autowired
    private CapitoloRepository capitoloRepository;

    @Autowired
    private StoriaRepository storiaRepository;

    public Capitolo saveCapitolo(CapitoloDto capitoloDto) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Storia storia = storiaRepository
                .findByIdAndAutore_Username(capitoloDto.getStoriaId(), username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a modificare questa storia"));

        Capitolo capitolo = new Capitolo();
        capitolo.setNumeroCapitolo(capitoloDto.getNumeroCapitolo());
        capitolo.setContenuto(capitoloDto.getContenuto());
        capitolo.setTitolo(capitoloDto.getTitolo());
        capitolo.setStoria(storia);

        return capitoloRepository.save(capitolo);
    }

    public List<Capitolo> listaCapitoli() {
        return capitoloRepository.findAll();
    }

    public Capitolo getCapitolo(int id) throws NotFoundException {
        return capitoloRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Il capitolo con id " + id + " non è stato trovato"));
    }

    public Capitolo updateCaptiolo(int id, CapitoloDto capitoloDto) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Capitolo capitolo = capitoloRepository.findByIdAndStoria_Autore_Username(id, username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a modificare questo capitolo"));

        capitolo.setContenuto(capitoloDto.getContenuto());
        capitolo.setNumeroCapitolo(capitoloDto.getNumeroCapitolo());
        capitolo.setTitolo(capitoloDto.getTitolo());

        return capitoloRepository.save(capitolo);
    }

    public void deleteCapitolo(int id) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Capitolo capitolo = capitoloRepository.findByIdAndStoria_Autore_Username(id, username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a cancellare questo capitolo"));

        capitoloRepository.delete(capitolo);
    }
}