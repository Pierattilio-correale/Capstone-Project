package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.CapitoloDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.model.Capitolo;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.repository.CapitoloRepository;
import it.epicode.Capstone_Project.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CapitoloService {

    @Autowired
    private CapitoloRepository capitoloRepository;
    @Autowired
private StoriaRepository storiaRepository;

    public Capitolo saveCapitolo(CapitoloDto capitoloDto) throws NotFoundException {
        Storia storia= storiaRepository.findById(capitoloDto.getStoriaId()).orElseThrow(()->new NotFoundException("la storia con id "+ capitoloDto.getStoriaId() +" non esiste"));
        Capitolo capitolo = new Capitolo();
        capitolo.setNumeroCapitolo(capitoloDto.getNumeroCapitolo());
        capitolo.setContenuto(capitoloDto.getContenuto());
        capitolo.setTitolo(capitoloDto.getTitolo());
        capitolo.setStoria(storia);

        return capitoloRepository.save(capitolo);
    }
    public List<Capitolo> listaCapitoli(){
      return  capitoloRepository.findAll();
    }
    public Capitolo getCapitolo(int id) throws NotFoundException {
  return capitoloRepository.findById(id).orElseThrow(()->new NotFoundException("il capitolo con id "+id+" non è stato trovato"));

    }
    public Capitolo updateCaptiolo(int id , CapitoloDto capitoloDto) throws NotFoundException {
        Capitolo capitoloDaAggiornare =getCapitolo(id);
        capitoloDaAggiornare.setContenuto(capitoloDto.getContenuto());
        capitoloDaAggiornare.setNumeroCapitolo(capitoloDto.getNumeroCapitolo());
        capitoloDaAggiornare.setTitolo(capitoloDto.getTitolo());

        return capitoloRepository.save(capitoloDaAggiornare);
    }

    public  void deleteCapitolo(int id) throws NotFoundException {
        Capitolo capitoloDaCancellare =getCapitolo(id);
        capitoloRepository.delete(capitoloDaCancellare);
    }
}
