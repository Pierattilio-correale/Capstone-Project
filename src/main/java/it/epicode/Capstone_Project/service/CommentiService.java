package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.CommentiDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.UnauthorizedException;
import it.epicode.Capstone_Project.model.Capitolo;
import it.epicode.Capstone_Project.model.Commenti;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.CapitoloRepository;
import it.epicode.Capstone_Project.repository.CommentiRepository;
import it.epicode.Capstone_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentiService {

    @Autowired
    private CommentiRepository commentiRepository;
    @Autowired
    private CapitoloRepository capitoloRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public Commenti saveCommento(CommentiDto commentiDto) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUser(commentiDto.getUserId());
        if (!user.getUsername().equals(username)) {
            throw new UnauthorizedException("Non puoi creare un commento per un altro utente");
        }

        Commenti commenti = new Commenti();
        commenti.setVoto(commentiDto.getVoto());
        commenti.setContenuto(commentiDto.getContenuto());

        Capitolo capitolo = capitoloRepository.findById(commentiDto.getCapitoloId())
                .orElseThrow(() -> new NotFoundException("Capitolo non trovato"));
        commenti.setCapitolo(capitolo);


        User autore = userRepository.findById(commentiDto.getUserId()).orElseThrow(() -> new NotFoundException("autore non trovato"));
        commenti.setAutore(autore);



        return commentiRepository.save(commenti);
    }

    public List<Commenti> getListaCommenti (){
        return commentiRepository.findAll();
    }

    public Commenti getCommento (int id) throws NotFoundException {
        return commentiRepository.findById(id).orElseThrow(() -> new NotFoundException("Il commento con id " + id + " non è stato trovato"));

    }

    public Commenti updateCommento (int id , CommentiDto commentiDto) throws NotFoundException {

        Commenti commentiDaAggiornare= getCommento(id);
        commentiDaAggiornare.setContenuto(commentiDto.getContenuto());
        commentiDaAggiornare.setVoto(commentiDto.getVoto());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!commentiDaAggiornare.getAutore().getUsername().equals(username)) {
            throw new UnauthorizedException("Non puoi modificare un commento non tuo");
        }

        return commentiRepository.save(commentiDaAggiornare);
    }

    public void deleteCommento(int id) throws NotFoundException {
        Commenti commentiDaCancellare = getCommento(id);
        commentiRepository.delete(commentiDaCancellare);
    }
    public List<Commenti> getCommentiByCapitoloId(int capitoloId) throws NotFoundException {
        if (!capitoloRepository.existsById(capitoloId)) {
            throw new NotFoundException("Capitolo non trovato");
        }
        return commentiRepository.findByCapitoloId(capitoloId);
    }
}
