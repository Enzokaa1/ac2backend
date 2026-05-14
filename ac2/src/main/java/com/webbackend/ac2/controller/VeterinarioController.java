package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.VeterinarioRequest;
import com.webbackend.ac2.model.Veterinario;
import com.webbackend.ac2.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {
    private final VeterinarioService veterinarioService;

    @PostMapping
    public Veterinario criar(@RequestBody VeterinarioRequest request) {
        return veterinarioService.criar(request);
    }

    @GetMapping
    public List<Veterinario> listar() {
        return veterinarioService.listar();
    }
}
