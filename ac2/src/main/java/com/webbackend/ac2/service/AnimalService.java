package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.AnimalRequest;
import com.webbackend.ac2.exception.RegraNegocioException;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Animal;
import com.webbackend.ac2.model.Tutor;
import com.webbackend.ac2.repository.AnimalRepository;
import com.webbackend.ac2.repository.ConsultaRepository;
import com.webbackend.ac2.repository.ProntuarioRepository;
import com.webbackend.ac2.repository.TutorRepository;
import com.webbackend.ac2.repository.VacinacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;
    private final ConsultaRepository consultaRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final VacinacaoRepository vacinacaoRepository;

    public Animal criar(AnimalRequest request) {
        if (request.getTutorId() == null) {
            throw new RegraNegocioException("Animal deve ter tutor válido.");
        }
        Tutor tutor = tutorRepository.findById(request.getTutorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor não encontrado"));
        Animal animal = new Animal();
        animal.setNome(request.getNome());
        animal.setEspecie(request.getEspecie());
        animal.setRaca(request.getRaca());
        animal.setIdade(request.getIdade());
        animal.setTutor(tutor);
        return animalRepository.save(animal);
    }

    public List<Animal> listar() {
        return animalRepository.findAll();
    }

    public Animal buscar(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal não encontrado"));
    }

    public Map<String, Object> historico(Long id) {
        Animal animal = buscar(id);
        return Map.of(
                "animal", animal,
                "tutor", animal.getTutor(),
                "consultas", consultaRepository.findByAnimalId(id),
                "prontuarios", prontuarioRepository.findByConsultaAnimalId(id),
                "vacinas", vacinacaoRepository.findByAnimalId(id)
        );
    }
}
