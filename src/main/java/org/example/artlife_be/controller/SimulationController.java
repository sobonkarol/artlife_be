package org.example.artlife_be.controller;

import org.example.artlife_be.model.WorldState;
import org.example.artlife_be.model.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/sim")
public class SimulationController {

    @Autowired
    private SimulationService simService;

    @PostMapping("/init")
    public void initialize(@RequestBody Map<String, Object> config) {
        simService.init(config);
    }

    @GetMapping("/next")
    public WorldState getNextStep() {
        return simService.processNextStep();
    }

    // Nowy endpoint do natychmiastowego odświeżania widoku
    @GetMapping("/state")
    public WorldState getState() {
        return simService.getCurrentState();
    }

    @PostMapping("/infect")
    public void infect() {
        simService.infect();
    }

    @PostMapping("/cure")
    public void cure() {
        simService.addLek();
    }
}