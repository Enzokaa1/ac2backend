package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.VacinacaoRequest;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Animal;
import com.webbackend.ac2.model.Vacinacao;
import com.webbackend.ac2.repository.AnimalRepository;
import com.webbackend.ac2.repository.VacinacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacinacaoService {
    private final VacinacaoRepository vacinacaoRepository;
    private final AnimalRepository animalRepository;

    public Vacinacao criar(VacinacaoRequest request) {
        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal não encontrado"));
        Vacinacao vacinacao = new Vacinacao();
        vacinacao.setAnimal(animal);
        vacinacao.setNomeVacina(request.getNomeVacina());
        vacinacao.setDataAplicacao(request.getDataAplicacao());
        vacinacao.setProximaDose(request.getProximaDose());
        vacinacao.setObservacoes(request.getObservacoes());
        return vacinacaoRepository.save(vacinacao);
    }

    public List<Vacinacao> listar() {
        return vacinacaoRepository.findAll();
    }
}
