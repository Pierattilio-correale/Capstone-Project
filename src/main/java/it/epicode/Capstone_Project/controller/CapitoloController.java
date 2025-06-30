package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.CapitoloDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.ValidationException;
import it.epicode.Capstone_Project.model.Capitolo;
import it.epicode.Capstone_Project.service.CapitoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capitoli")
public class CapitoloController {
    @Autowired
    private CapitoloService capitoloService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Capitolo saveCapitolo(@RequestBody @Validated CapitoloDto capitoloDto , BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
          throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",( s,e)->s+e));
        }
 return capitoloService.saveCapitolo(capitoloDto);
    }

    @GetMapping("")
    public List<Capitolo> listaCapitoli(){
        return capitoloService.listaCapitoli();
    }

    @GetMapping("/{id}")
    public Capitolo getCapitolo(@PathVariable int id) throws NotFoundException {
return capitoloService.getCapitolo(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Capitolo updateCapitolo(@PathVariable int id, @RequestBody @Validated CapitoloDto capitoloDto ,BindingResult bindingResult ) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",( s,e)->s+e));
        }
        return capitoloService.updateCaptiolo(id,capitoloDto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public void deleteCapitolo(@PathVariable  int id) throws NotFoundException {
        capitoloService.deleteCapitolo(id);
    }
}
