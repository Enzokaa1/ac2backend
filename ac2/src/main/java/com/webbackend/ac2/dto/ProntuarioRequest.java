package com.webbackend.ac2.dto;

import lombok.Data;

@Data
public class ProntuarioRequest {
    private Long consultaId;
    private String descricao;
    private String diagnostico;
    private String tratamento;
}
