package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.TutorRequest;
import com.webbackend.ac2.model.Tutor;
import com.webbackend.ac2.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;

    @PostMapping
    public Tutor criar(@RequestBody TutorRequest request) {
        return tutorService.criar(request);
    }

    @GetMapping
    public List<Tutor> listar() {
        return tutorService.listar();
    }

    @GetMapping("/{id}")
    public Tutor buscar(@PathVariable Long id) {
        return tutorService.buscar(id);
    }
}
