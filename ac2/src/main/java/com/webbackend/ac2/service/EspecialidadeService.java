package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.EspecialidadeRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.model.Especialidade;
import com.webbackend.ac2.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;

    public Especialidade criar(EspecialidadeRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome da especialidade é obrigatório.");
        }
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(request.getNome().trim());
        return especialidadeRepository.save(especialidade);
    }

    public List<Especialidade> listar() {
        return especialidadeRepository.findAll();
    }
}
