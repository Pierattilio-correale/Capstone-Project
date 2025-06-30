package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.StoriaDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.CapitoloRepository;
import it.epicode.Capstone_Project.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class StoriaService {
    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private CapitoloRepository capitoloRepository;
    @Autowired
    private UserService userService;

    public Storia saveStoria(StoriaDto storiaDto) throws NotFoundException {
        User user= userService.getUser(storiaDto.getUserId());
        if(user.getId()!= storiaDto.getUserId()){
            throw new NotFoundException("questa storia è associata a un utente sbagliato o inesistente");

        }
        Storia storia = new Storia();
        storia.setAutore(user);
        storia.setTitolo(storiaDto.getTitolo());
        storia.setDataCreazione(LocalDateTime.now());
        storia.setDescrizione(storiaDto.getDescrizione());

        return storiaRepository.save(storia);



    }
    public List<Storia> listaStorie(){
        return storiaRepository.findAll();
    }
    public Storia getStoria(int id) throws NotFoundException {
       return storiaRepository.findById(id).orElseThrow(()->new NotFoundException("la storia con id "+id+ " non è stata trovata"));
    }

    public Storia updateStoria(int id, StoriaDto storiaDto ) throws NotFoundException {
Storia storiaDaAggiornare = getStoria(id);
storiaDaAggiornare.setDescrizione(storiaDto.getDescrizione());
storiaDaAggiornare.setTitolo(storiaDto.getTitolo());

return storiaRepository.save(storiaDaAggiornare);
    }

    public void  deleteStoria(int id) throws NotFoundException {
        Storia storiaDaCancellare = getStoria(id);
        storiaRepository.delete(storiaDaCancellare);
    }
}
