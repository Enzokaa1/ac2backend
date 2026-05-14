package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.FuncionarioRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Funcionario;
import com.webbackend.ac2.model.Setor;
import com.webbackend.ac2.repository.FuncionarioRepository;
import com.webbackend.ac2.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    public Funcionario criar(FuncionarioRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do funcionário é obrigatório.");
        }
        if (request.getSetorId() == null) {
            throw new RegraNegocioException("Setor do funcionário é obrigatório.");
        }
        Setor setor = setorRepository.findById(request.getSetorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado"));
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(request.getNome().trim());
        funcionario.setSetor(setor);
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }
}
