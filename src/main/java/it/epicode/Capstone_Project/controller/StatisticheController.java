package it.epicode.Capstone_Project.controller;

import it.epicode.Capstone_Project.dto.StatisticheDto;
import it.epicode.Capstone_Project.service.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistiche")
public class StatisticheController {

    @Autowired
    private StatisticheService statisticheService;

    @GetMapping("/mie")
    public ResponseEntity<StatisticheDto> getMieStatistiche() {
        return ResponseEntity.ok(statisticheService.getStatistichePersonali());
    }
    @GetMapping("/globali")
    public ResponseEntity<StatisticheDto> getStatisticheGlobali() {
        return ResponseEntity.ok(statisticheService.getStatisticheGlobali());
    }
}