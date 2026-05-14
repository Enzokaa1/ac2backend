package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.TutorRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Tutor;
import com.webbackend.ac2.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final TutorRepository tutorRepository;

    public Tutor criar(TutorRequest request) {
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome do tutor é obrigatório.");
        }
        Tutor tutor = new Tutor();
        tutor.setNome(request.getNome().trim());
        tutor.setTelefone(request.getTelefone());
        return tutorRepository.save(tutor);
    }

    public List<Tutor> listar() {
        return tutorRepository.findAll();
    }

    public Tutor buscar(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor não encontrado"));
    }
}
