package com.webbackend.ac2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnoreProperties({"consultas", "vacinas"})
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    @JsonIgnoreProperties("consultas")
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;

    private LocalDateTime dataHora;
    private String motivo;

    @Enumerated(EnumType.STRING)
    private ConsultaStatus status;
}
