package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.ProjetoRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Funcionario;
import com.webbackend.ac2.model.Projeto;
import com.webbackend.ac2.repository.FuncionarioRepository;
import com.webbackend.ac2.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public Projeto criar(ProjetoRequest request) {
        if (request.getDescricao() == null || request.getDescricao().trim().isEmpty()) {
            throw new RegraNegocioException("Descrição do projeto é obrigatória.");
        }
        if (request.getDataInicio() == null || request.getDataFim() == null) {
            throw new RegraNegocioException("Data de início e fim são obrigatórias.");
        }
        if (request.getDataFim().isBefore(request.getDataInicio())) {
            throw new RegraNegocioException("Data final não pode ser anterior à data inicial.");
        }
        Projeto projeto = new Projeto();
        projeto.setDescricao(request.getDescricao().trim());
        projeto.setDataInicio(request.getDataInicio());
        projeto.setDataFim(request.getDataFim());
        return projetoRepository.save(projeto);
    }

    public Projeto buscarComFuncionarios(Long id) {
        return projetoRepository.buscarProjetoComFuncionarios(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
    }

    public List<Projeto> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return projetoRepository.findByDataInicioGreaterThanEqualAndDataFimLessThanEqual(inicio, fim);
    }

    public Projeto vincularFuncionario(Long projetoId, Long funcionarioId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
        boolean jaExiste = projeto.getFuncionarios().stream().anyMatch(f -> f.getId().equals(funcionarioId));
        if (jaExiste) {
            throw new RegraNegocioException("Funcionário já está vinculado ao projeto.");
        }
        projeto.getFuncionarios().add(funcionario);
        return projetoRepository.save(projeto);
    }

    public List<Projeto> buscarPorFuncionario(Long funcionarioId) {
        return projetoRepository.buscarProjetosPorFuncionario(funcionarioId);
    }

    public List<Projeto> listar() {
        return projetoRepository.findAll();
    }
}
