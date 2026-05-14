package com.webbackend.ac2.controller;

import com.webbackend.ac2.dto.ConsultaRequest;
import com.webbackend.ac2.model.Consulta;
import com.webbackend.ac2.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final ConsultaService consultaService;

    @PostMapping
    public Consulta criar(@RequestBody ConsultaRequest request) {
        return consultaService.criar(request);
    }

    @GetMapping
    public List<Consulta> listar() {
        return consultaService.listar();
    }

    @GetMapping("/{id}")
    public Consulta buscar(@PathVariable Long id) {
        return consultaService.buscar(id);
    }
}
