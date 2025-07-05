package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.UserDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.ValidationException;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/users")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }
    @GetMapping("users/{id}")
    public User getUser(@PathVariable int id) throws NotFoundException {
        return  userService.getUser(id);
    }
    @PutMapping("users/{id}")
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
}

