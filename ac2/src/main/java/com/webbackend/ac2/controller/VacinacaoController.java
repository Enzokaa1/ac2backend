package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.VacinacaoRequest;
import com.webbackend.ac2.model.Vacinacao;
import com.webbackend.ac2.service.VacinacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacinacoes")
@RequiredArgsConstructor
public class VacinacaoController {
    private final VacinacaoService vacinacaoService;

    @PostMapping
    public Vacinacao criar(@RequestBody VacinacaoRequest request) {
        return vacinacaoService.criar(request);
    }

    @GetMapping
    public List<Vacinacao> listar() {
        return vacinacaoService.listar();
    }
}
