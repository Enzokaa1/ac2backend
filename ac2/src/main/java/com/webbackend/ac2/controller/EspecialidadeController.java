package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.EspecialidadeRequest;
import com.webbackend.ac2.model.Especialidade;
import com.webbackend.ac2.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {
    private final EspecialidadeService especialidadeService;

    @PostMapping
    public Especialidade criar(@RequestBody EspecialidadeRequest request) {
        return especialidadeService.criar(request);
    }

    @GetMapping
    public List<Especialidade> listar() {
        return especialidadeService.listar();
    }
}
