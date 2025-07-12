package it.epicode.Capstone_Project.service;

import com.cloudinary.Cloudinary;
import it.epicode.Capstone_Project.dto.DescrizioneDto;
import it.epicode.Capstone_Project.dto.UpdateBaseUserDto;
import it.epicode.Capstone_Project.dto.UserDto;
import it.epicode.Capstone_Project.enumerated.Role;
import it.epicode.Capstone_Project.exception.AlreadyExistException;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.UnauthorizedException;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

public User saveUser(UserDto userDto) throws AlreadyExistException {
User user = new User();

if(userRepository.existsByEmail(userDto.getEmail())){
    throw new AlreadyExistException("email già esistente");
}
if(userRepository.existsByUsername(userDto.getUsername())){
    throw new AlreadyExistException("username già esistente");

}
user.setRole(Role.AUTHOR);
user.setAvatar("https://ui-avatars.com/api/?name="+userDto.getUsername());
user.setNome(userDto.getNome());
user.setUsername(userDto.getUsername());
user.setDataNascita(userDto.getDataNascita());
user.setCognome(userDto.getCognome());
user.setEmail(userDto.getEmail());
user.setPassword(passwordEncoder.encode(userDto.getPassword()));
user.setDescrizione("");

return userRepository.save(user);
}

public List<User> getAllUser(){
    return userRepository.findAll();
}

public User getUser(int id)throws NotFoundException {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("L'utente con ID " + id + " non è stato trovato"));
    }

    public User updateUser(int id , UserDto userDto) throws NotFoundException{
        User userDaAggiornare = getUser(id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameAutenticato;

        if (principal instanceof UserDetails) {
            usernameAutenticato = ((UserDetails) principal).getUsername();
        } else {
            usernameAutenticato = principal.toString();
        }

        User userAutenticato = userRepository.findByUsernameAndEmail(usernameAutenticato, userDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Utente autenticato non trovato"));


        boolean isAdmin = userAutenticato.getRole() == Role.ADMIN;
        boolean isStessoUtente = userAutenticato.getId() == userDaAggiornare.getId();

        if (!isAdmin && !isStessoUtente) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo utente");
        }

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setUsername(userDto.getUsername());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setEmail(userDto.getEmail());
        userDaAggiornare.setDataNascita(userDto.getDataNascita());
        userDaAggiornare.setDescrizione(userDto.getDescrizione());
        userDaAggiornare.setAvatar("https://ui-avatars.com/api/?name="+userDto.getUsername());
        if(!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())){
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }


        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id)throws NotFoundException{
        User userDaEliminare = getUser(id);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameAutenticato;

        if (principal instanceof UserDetails) {
            usernameAutenticato = ((UserDetails) principal).getUsername();
        } else {
            usernameAutenticato = principal.toString();
        }

        User userAutenticato = userRepository.findByUsername(usernameAutenticato)
                .orElseThrow(() -> new NotFoundException("Utente autenticato non trovato"));

        boolean isAdmin = userAutenticato.getRole() == Role.ADMIN;
        boolean isStessoUtente = userAutenticato.getId() == userDaEliminare.getId();

        if (!isAdmin && !isStessoUtente) {
            throw new UnauthorizedException("Non sei autorizzato a eliminare questo utente");
        }
        userRepository.delete(userDaEliminare);
    }


    public User aggiornaDescrizione(int id, DescrizioneDto dto) throws NotFoundException {
        User user = getUser(id);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameAutenticato;

        if (principal instanceof UserDetails) {
            usernameAutenticato = ((UserDetails) principal).getUsername();
        } else {
            usernameAutenticato = principal.toString();
        }

        User userAutenticato = userRepository.findByUsername(usernameAutenticato)
                .orElseThrow(() -> new NotFoundException("Utente autenticato non trovato"));

        boolean isAdmin = userAutenticato.getRole() == Role.ADMIN;
        boolean isStessoUtente = userAutenticato.getId() == user.getId();

        if (!isAdmin && !isStessoUtente) {
            throw new UnauthorizedException("Non sei autorizzato a modificare la descrizione di questo utente");
        }


        user.setDescrizione(dto.getDescrizione());
        return userRepository.save(user);
    }

    public String patchImgUser(int id, MultipartFile file) throws NotFoundException, IOException {

        User user = getUser(id);


        String usernameAutenticato = SecurityContextHolder.getContext().getAuthentication().getName();


        User userAutenticato = userRepository.findByUsername(usernameAutenticato)
                .orElseThrow(() -> new NotFoundException("Utente autenticato non trovato"));


        boolean isAdmin = userAutenticato.getRole() == Role.ADMIN;
        boolean isStessoUtente = userAutenticato.getId() == user.getId();

        if (!isAdmin && !isStessoUtente) {
            throw new UnauthorizedException("Non sei autorizzato a modificare l'immagine di questo utente");
        }


        String url = (String) cloudinary.uploader()
                .upload(file.getBytes(), Collections.emptyMap())
                .get("url");


        user.setAvatar(url);
        userRepository.save(user);

        return url;
    }
    public User patchBaseUserData(int id, UpdateBaseUserDto dto) throws NotFoundException {
        User user = getUser(id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameAutenticato = principal instanceof UserDetails
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        User userAutenticato = userRepository.findByUsername(usernameAutenticato)
                .orElseThrow(() -> new NotFoundException("Utente autenticato non trovato"));

        boolean isAdmin = userAutenticato.getRole() == Role.ADMIN;
        boolean isStessoUtente = userAutenticato.getId() == user.getId();

        if (!isAdmin && !isStessoUtente) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo utente");
        }

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getDataNascita() != null) {
            user.setDataNascita(dto.getDataNascita());
        }

        return userRepository.save(user);
    }
}

