package com.webbackend.ac2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {
    private Long animalId;
    private Long veterinarioId;
    private Long especialidadeId;
    private LocalDateTime dataHora;
    private String motivo;
}
