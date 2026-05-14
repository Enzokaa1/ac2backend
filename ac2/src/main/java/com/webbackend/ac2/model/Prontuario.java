package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "consulta_id")
    @JsonIgnoreProperties({"animal", "veterinario", "especialidade"})
    private Consulta consulta;

    private String descricao;
    private String diagnostico;
    private String tratamento;
    private LocalDateTime dataRegistro;
}
