package com.webbackend.ac2.dto;

import lombok.Data;

@Data
public class AnimalRequest {
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private Long tutorId;
}
