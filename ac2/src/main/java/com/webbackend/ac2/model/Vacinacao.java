package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Vacinacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnoreProperties({"consultas", "vacinas"})
    private Animal animal;

    private String nomeVacina;
    private LocalDate dataAplicacao;
    private LocalDate proximaDose;
    private String observacoes;
}
