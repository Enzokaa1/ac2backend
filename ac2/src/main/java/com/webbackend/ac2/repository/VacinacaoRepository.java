package com.webbackend.ac2.repository;

import com.webbackend.ac2.model.Vacinacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacinacaoRepository extends JpaRepository<Vacinacao, Long> {
    List<Vacinacao> findByAnimalId(Long animalId);
}
