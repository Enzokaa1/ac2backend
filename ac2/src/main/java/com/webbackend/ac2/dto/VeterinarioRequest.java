package com.webbackend.ac2.dto;

import lombok.Data;

@Data
public class VeterinarioRequest {
    private String nome;
    private String crmv;
    private Long especialidadeId;
}
