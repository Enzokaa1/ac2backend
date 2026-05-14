package com.webbackend.ac2.repository;

import com.webbackend.ac2.model.Consulta;
import com.webbackend.ac2.model.ConsultaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByVeterinarioIdAndDataHoraAndStatus(Long veterinarioId, LocalDateTime dataHora, ConsultaStatus status);
    List<Consulta> findByAnimalId(Long animalId);
}
