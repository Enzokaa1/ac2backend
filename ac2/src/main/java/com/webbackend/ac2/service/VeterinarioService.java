package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.VeterinarioRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Especialidade;
import com.webbackend.ac2.model.Veterinario;
import com.webbackend.ac2.repository.EspecialidadeRepository;
import com.webbackend.ac2.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public Veterinario criar(VeterinarioRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do veterinário é obrigatório.");
        }
        if (request.getEspecialidadeId() == null) {
            throw new RegraNegocioException("Especialidade do veterinário é obrigatória.");
        }
        Especialidade especialidade = especialidadeRepository.findById(request.getEspecialidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade não encontrada"));
        Veterinario veterinario = new Veterinario();
        veterinario.setNome(request.getNome().trim());
        veterinario.setCrmv(request.getCrmv());
        veterinario.setEspecialidade(especialidade);
        return veterinarioRepository.save(veterinario);
    }

    public List<Veterinario> listar() {
        return veterinarioRepository.findAll();
    }
}
