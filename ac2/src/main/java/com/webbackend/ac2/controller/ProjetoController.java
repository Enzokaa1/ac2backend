package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.ProjetoRequest;
import com.webbackend.ac2.model.Projeto;
import com.webbackend.ac2.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
public class ProjetoController {
    private final ProjetoService projetoService;

    @PostMapping
    public Projeto criar(@RequestBody ProjetoRequest request) {
        return projetoService.criar(request);
    }

    @GetMapping("/{id}")
    public Projeto buscar(@PathVariable Long id) {
        return projetoService.buscarComFuncionarios(id);
    }

    @GetMapping
    public List<Projeto> listar() {
        return projetoService.listar();
    }

    @GetMapping("/periodo")
    public List<Projeto> buscarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return projetoService.buscarPorPeriodo(inicio, fim);
    }

    @PostMapping("/{projetoId}/funcionarios/{funcionarioId}")
    public Projeto vincular(@PathVariable Long projetoId, @PathVariable Long funcionarioId) {
        return projetoService.vincularFuncionario(projetoId, funcionarioId);
    }
}
