package com.webbackend.ac2.repository;

import com.webbackend.ac2.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.funcionarios WHERE p.id = :id")
    Optional<Projeto> buscarProjetoComFuncionarios(Long id);

    List<Projeto> findByDataInicioGreaterThanEqualAndDataFimLessThanEqual(LocalDate inicio, LocalDate fim);

    @Query("SELECT p FROM Projeto p JOIN p.funcionarios f WHERE f.id = :funcionarioId")
    List<Projeto> buscarProjetosPorFuncionario(Long funcionarioId);
}
