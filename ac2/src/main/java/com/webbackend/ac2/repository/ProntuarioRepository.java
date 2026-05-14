package com.webbackend.ac2.repository;

import com.webbackend.ac2.model.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    List<Prontuario> findByConsultaAnimalId(Long animalId);
}
