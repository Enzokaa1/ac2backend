package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnoreProperties("animais")
    private Tutor tutor;

    @OneToMany(mappedBy = "animal")
    @JsonIgnoreProperties({"animal", "veterinario", "especialidade"})
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany(mappedBy = "animal")
    @JsonIgnoreProperties("animal")
    private List<Vacinacao> vacinas = new ArrayList<>();
}
