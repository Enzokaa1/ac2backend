package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;

    @OneToMany(mappedBy = "tutor")
    @JsonIgnoreProperties({"tutor", "consultas", "vacinas"})
    private List<Animal> animais = new ArrayList<>();
}
