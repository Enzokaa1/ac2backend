package com.webbackend.ac2.service;

import com.webbackend.ac2.dto.ProntuarioRequest;
import com.webbackend.ac2.exception.RecursoNaoEncontradoException;
import com.webbackend.ac2.model.Consulta;
import com.webbackend.ac2.model.ConsultaStatus;
import com.webbackend.ac2.model.Prontuario;
import com.webbackend.ac2.repository.ConsultaRepository;
import com.webbackend.ac2.repository.ProntuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProntuarioService {
    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;

    public Prontuario criar(ProntuarioRequest request) {
        Consulta consulta = consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        Prontuario prontuario = new Prontuario();
        prontuario.setConsulta(consulta);
        prontuario.setDescricao(request.getDescricao());
        prontuario.setDiagnostico(request.getDiagnostico());
        prontuario.setTratamento(request.getTratamento());
        prontuario.setDataRegistro(LocalDateTime.now());
        consulta.setStatus(ConsultaStatus.REALIZADA);
        consultaRepository.save(consulta);
        return prontuarioRepository.save(prontuario);
    }

    public List<Prontuario> listar() {
        return prontuarioRepository.findAll();
    }
}
