package it.epicode.Capstone_Project.service;

import it.epicode.Capstone_Project.dto.LoginDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.repository.UserRepository;
import it.epicode.Capstone_Project.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//
@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public  String login(LoginDto loginDto) throws NotFoundException {
        User user =userRepository.findByUsernameAndEmail((loginDto.getUsername()) , loginDto.getEmail()).orElseThrow(()->new NotFoundException("l'utente con questo username/password non esiste"));
        if((passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))){


            return jwtTool.createToken(user);
        }else{
            throw new NotFoundException("l'utente con questo username/password non esiste");
        }
    }
}
