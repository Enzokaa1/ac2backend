package com.webbackend.ac2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
