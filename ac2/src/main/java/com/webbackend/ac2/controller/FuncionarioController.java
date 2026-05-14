package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.FuncionarioRequest;
import com.webbackend.ac2.model.Funcionario;
import com.webbackend.ac2.model.Projeto;
import com.webbackend.ac2.service.FuncionarioService;
import com.webbackend.ac2.service.ProjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    private final ProjetoService projetoService;

    @PostMapping
    public Funcionario criar(@RequestBody FuncionarioRequest request) {
        return funcionarioService.criar(request);
    }

    @GetMapping
    public List<Funcionario> listar() {
        return funcionarioService.listar();
    }

    @GetMapping("/{id}/projetos")
    public List<Projeto> projetos(@PathVariable Long id) {
        return projetoService.buscarPorFuncionario(id);
    }
}
