package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.DescrizioneDto;
import it.epicode.Capstone_Project.dto.UpdateBaseUserDto;
import it.epicode.Capstone_Project.dto.UserDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.ValidationException;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("")
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws NotFoundException {
        return  userService.getUser(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public User aggiornaUser(@PathVariable int id , @RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }
        return  userService.updateUser(id,userDto);


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public void deleteUser(@PathVariable int id) throws NotFoundException {
        userService.deleteUser(id);
    }
    @PatchMapping("/{id}/descrizione")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public User aggiornaDescrizione(
            @PathVariable int id,
            @RequestBody @Validated DescrizioneDto descrizioneDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }

       return userService.aggiornaDescrizione(id,descrizioneDto);

    }
    @PatchMapping("/{id}")
    public String userImgProfilo(@PathVariable int id ,@RequestBody MultipartFile file) throws NotFoundException, IOException {
        return userService.patchImgUser(id,file);
    }
    @PatchMapping("/{id}/base")
    public User patchUserBaseInfo(@PathVariable int id,
                                                  @RequestBody UpdateBaseUserDto updateBaseUserDto) throws NotFoundException {
        return userService.patchBaseUserData(id, updateBaseUserDto);

    }
}

