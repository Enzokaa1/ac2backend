package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.SetorRequest;
import com.webbackend.ac2.model.Setor;
import com.webbackend.ac2.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {
    private final SetorService setorService;

    @PostMapping
    public Setor criar(@RequestBody SetorRequest request) {
        return setorService.criar(request);
    }

    @GetMapping
    public List<Setor> listar() {
        return setorService.listarComFuncionarios();
    }

    @GetMapping("/{id}")
    public Setor buscar(@PathVariable Long id) {
        return setorService.buscarPorId(id);
    }
}
