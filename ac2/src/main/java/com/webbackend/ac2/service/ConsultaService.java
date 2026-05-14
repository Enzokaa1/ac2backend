package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.ConsultaRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.*;
import com.webbackend.ac2.repository.AnimalRepository;
import com.webbackend.ac2.repository.ConsultaRepository;
import com.webbackend.ac2.repository.EspecialidadeRepository;
import com.webbackend.ac2.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public Consulta criar(ConsultaRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal não encontrado"));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veterinário não encontrado"));
        Especialidade especialidade = especialidadeRepository.findById(request.getEspecialidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade não encontrada"));

        if (!veterinario.getEspecialidade().getId().equals(especialidade.getId())) {
            throw new RegraNegocioException("Veterinário não atende a especialidade informada.");
        }
        boolean conflita = consultaRepository.existsByVeterinarioIdAndDataHoraAndStatus(
                veterinario.getId(), request.getDataHora(), ConsultaStatus.AGENDADA
        );
        if (conflita) {
            throw new RegraNegocioException("Veterinário já possui consulta agendada para este horário.");
        }

        Consulta consulta = new Consulta();
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        consulta.setEspecialidade(especialidade);
        consulta.setDataHora(request.getDataHora());
        consulta.setMotivo(request.getMotivo());
        consulta.setStatus(ConsultaStatus.AGENDADA);
        return consultaRepository.save(consulta);
    }

    public List<Consulta> listar() {
        return consultaRepository.findAll();
    }

    public Consulta buscar(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
    }
}
