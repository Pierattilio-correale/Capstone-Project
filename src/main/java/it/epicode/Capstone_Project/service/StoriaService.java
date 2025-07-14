package it.epicode.Capstone_Project.service;

import com.cloudinary.Cloudinary;
import it.epicode.Capstone_Project.dto.StoriaDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.UnauthorizedException;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.CapitoloRepository;
import it.epicode.Capstone_Project.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class StoriaService {

    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private CapitoloRepository capitoloRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    public Storia saveStoria(StoriaDto storiaDto) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUser(storiaDto.getUserId());
        if (!user.getUsername().equals(username)) {
            throw new UnauthorizedException("Non puoi creare una storia per un altro utente");
        }

        Storia storia = new Storia();
        storia.setAutore(user);
        storia.setTitolo(storiaDto.getTitolo());
        storia.setDataCreazione(LocalDateTime.now());
        storia.setDescrizione(storiaDto.getDescrizione());
        storia.setGenere(storiaDto.getGenere());
        storia.setImmagineCopertina("https://dummyimage.com/512x800/cccccc/000000&text=Book+Cover");

        return storiaRepository.save(storia);
    }

    public List<Storia> listaStorie() {
        return storiaRepository.findAll();
    }

    public Storia getStoria(int id) throws NotFoundException {
        return storiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La storia con id " + id + " non è stata trovata"));
    }

    public Storia updateStoria(int id, StoriaDto storiaDto) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Storia storia = storiaRepository.findByIdAndAutore_Username(id, username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a modificare questa storia"));

        storia.setDescrizione(storiaDto.getDescrizione());
        storia.setTitolo(storiaDto.getTitolo());
        storia.setGenere(storiaDto.getGenere());

        return storiaRepository.save(storia);
    }

    public void deleteStoria(int id) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Storia storia = storiaRepository.findByIdAndAutore_Username(id, username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a cancellare questa storia"));

        storiaRepository.delete(storia);
    }

    public String patchStoria(int id, MultipartFile file) throws NotFoundException, IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Storia storia = storiaRepository.findByIdAndAutore_Username(id, username)
                .orElseThrow(() -> new UnauthorizedException("Non sei autorizzato a modificare questa storia"));

        String url = (String) cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");
        storia.setImmagineCopertina(url);
        storiaRepository.save(storia);

        return url;
    }
    public List<Storia> cercaPerTitolo(String titolo) {
        return storiaRepository.findByTitoloContainingIgnoreCase(titolo);
    }
}