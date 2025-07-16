package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.StatisticheDto;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.repository.UserRepository;
import it.epicode.Capstone_Project.service.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistiche")
public class StatisticheController {

    @Autowired
    private StatisticheService statisticheService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<StatisticheDto> getStatisticheUtente(@PathVariable int id) {
        return ResponseEntity.ok(statisticheService.getStatistichePersonali(id));
    }
    @GetMapping("/globali")
    public ResponseEntity<StatisticheDto> getStatisticheGlobali() {
        return ResponseEntity.ok(statisticheService.getStatisticheGlobali());
    }
}