package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.SetorRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Setor;
import com.webbackend.ac2.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {
    private final SetorRepository setorRepository;

    public Setor criar(SetorRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do setor é obrigatório.");
        }
        Setor setor = new Setor();
        setor.setNome(request.getNome().trim());
        return setorRepository.save(setor);
    }

    public List<Setor> listarComFuncionarios() {
        return setorRepository.listarSetoresComFuncionarios();
    }

    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado"));
    }
}
