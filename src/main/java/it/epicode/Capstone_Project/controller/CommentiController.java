package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.CommentiDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.exception.ValidationException;
import it.epicode.Capstone_Project.model.Commenti;
import it.epicode.Capstone_Project.service.CommentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/commenti")
public class CommentiController {

    @Autowired
    private CommentiService commentiService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Commenti saveCommento (@RequestBody @Validated CommentiDto commentiDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw  new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s, e)->s+e));
        }
        return commentiService.saveCommento(commentiDto);
    }

    @GetMapping("")
    public List<Commenti> getAllCommenti(){
        return  commentiService.getListaCommenti();
    }

    @GetMapping("/{id}")
   
    public Commenti getCommento(@PathVariable int id) throws NotFoundException {
        return  commentiService.getCommento(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public Commenti aggiornaCommento(@PathVariable int id , @RequestBody @Validated CommentiDto commentiDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }
        return  commentiService.updateCommento(id,commentiDto);


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHOR', 'ADMIN')")
    public void deleteCommento(@PathVariable int id) throws NotFoundException {
        commentiService.deleteCommento(id);
    }

    @GetMapping("/capitolo/{capitoloId}")
    public List<Commenti> getCommentiByCapitolo(@PathVariable int capitoloId) throws NotFoundException {
        return commentiService.getCommentiByCapitoloId(capitoloId);
    }

}
