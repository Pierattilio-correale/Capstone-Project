package it.epicode.Capstone_Project.controller;


import it.epicode.Capstone_Project.dto.StoriaDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.ValidationException;
import it.epicode.Capstone_Project.model.Storia;
import it.epicode.Capstone_Project.service.StoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/storie")
public class StoriaController {
    @Autowired
    private StoriaService storiaService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Storia saveStoria(@RequestBody @Validated StoriaDto storiaDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s, e)->s+e));
        }
        return storiaService.saveStoria(storiaDto);
    }
    @GetMapping("")
    public List<Storia> listaStorie(){
        return storiaService.listaStorie();
    }

    @GetMapping("/{id}")
    public Storia getStoria(@PathVariable int id) throws NotFoundException {
        return storiaService.getStoria(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Storia updateStoria(@PathVariable int id, @RequestBody @Validated StoriaDto storiaDto , BindingResult bindingResult ) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",( s,e)->s+e));
        }
        return storiaService.updateStoria(id,storiaDto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public void deleteStoria(@PathVariable  int id) throws NotFoundException {
        storiaService.deleteStoria(id);
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public String patchStoria(@PathVariable int id, @RequestParam("file") MultipartFile file) throws NotFoundException, IOException {
        return storiaService.patchStoria(id,file);

    }
    @GetMapping("/search")
    public List<Storia> cercaPerTitolo(@RequestParam String titolo) {
        return storiaService.cercaPerTitolo(titolo);
    }
}
