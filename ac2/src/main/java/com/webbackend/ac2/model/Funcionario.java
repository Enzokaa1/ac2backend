package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @JsonIgnoreProperties("funcionarios")
    private Setor setor;

    @ManyToMany(mappedBy = "funcionarios")
    @JsonIgnoreProperties("funcionarios")
    private List<Projeto> projetos = new ArrayList<>();
}
