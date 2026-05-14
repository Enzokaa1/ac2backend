package com.webbackend.ac2.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacinacaoRequest {
    private Long animalId;
    private String nomeVacina;
    private LocalDate dataAplicacao;
    private LocalDate proximaDose;
    private String observacoes;
}
