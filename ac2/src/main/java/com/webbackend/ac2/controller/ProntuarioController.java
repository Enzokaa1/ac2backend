package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.ProntuarioRequest;
import com.webbackend.ac2.model.Prontuario;
import com.webbackend.ac2.service.ProntuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
@RequiredArgsConstructor
public class ProntuarioController {
    private final ProntuarioService prontuarioService;

    @PostMapping
    public Prontuario criar(@RequestBody ProntuarioRequest request) {
        return prontuarioService.criar(request);
    }

    @GetMapping
    public List<Prontuario> listar() {
        return prontuarioService.listar();
    }
}
