package com.webbackend.ac2.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjetoRequest {
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
