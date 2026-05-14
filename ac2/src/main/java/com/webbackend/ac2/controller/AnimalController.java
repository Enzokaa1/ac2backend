package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.AnimalRequest;
import com.webbackend.ac2.model.Animal;
import com.webbackend.ac2.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping
    public Animal criar(@RequestBody AnimalRequest request) {
        return animalService.criar(request);
    }

    @GetMapping
    public List<Animal> listar() {
        return animalService.listar();
    }

    @GetMapping("/{id}")
    public Animal buscar(@PathVariable Long id) {
        return animalService.buscar(id);
    }

    @GetMapping("/{id}/historico")
    public Map<String, Object> historico(@PathVariable Long id) {
        return animalService.historico(id);
    }
}
