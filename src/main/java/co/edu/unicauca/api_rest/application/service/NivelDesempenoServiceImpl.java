package co.edu.unicauca.api_rest.application.service;


import co.edu.unicauca.api_rest.application.dto.request.NivelDesempenoRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.NivelDesempenoResponseDto;
import co.edu.unicauca.api_rest.application.service.NivelDesempenoService;
import co.edu.unicauca.api_rest.domain.model.CriterioEvaluacion;
import co.edu.unicauca.api_rest.domain.model.NivelDesempeno;
import co.edu.unicauca.api_rest.domain.repository.CriterioEvaluacionRepository;
import co.edu.unicauca.api_rest.domain.repository.NivelDesempenoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NivelDesempenoServiceImpl implements NivelDesempenoService {

    private final NivelDesempenoRepository nivelDesempenoRepository;
    private final CriterioEvaluacionRepository criterioEvaluacionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public NivelDesempenoServiceImpl(NivelDesempenoRepository nivelDesempenoRepository,
                                      CriterioEvaluacionRepository criterioEvaluacionRepository,
                                      ModelMapper modelMapper) {
        this.nivelDesempenoRepository = nivelDesempenoRepository;
        this.criterioEvaluacionRepository = criterioEvaluacionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public NivelDesempenoResponseDto createNivelDesempeno(NivelDesempenoRequestDto nivelDesempenoDto) {
        // Convertir DTO a entidad
        NivelDesempeno nivelDesempeno = modelMapper.map(nivelDesempenoDto, NivelDesempeno.class);

        // Validación de negocio: Asegurar que el criterio existe
        CriterioEvaluacion criterio = criterioEvaluacionRepository.findById(nivelDesempenoDto.getCriterioEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Criterio de evaluación con ID " + nivelDesempenoDto.getCriterioEvaluacionId() + " no encontrado."));

        // Validar rangos de nota
        if (nivelDesempeno.getNotaMinima().compareTo(nivelDesempeno.getNotaMaxima()) > 0) {
            throw new IllegalArgumentException("La nota mínima no puede ser mayor que la nota máxima.");
        }

        nivelDesempeno.setId(UUID.randomUUID().toString());
        nivelDesempeno.setCriterioEvaluacion(criterio); // Asociar la entidad CriterioEvaluacion cargada

        NivelDesempeno savedNivel = nivelDesempenoRepository.save(nivelDesempeno);
        return modelMapper.map(savedNivel, NivelDesempenoResponseDto.class);
    }

    @Override
    public Optional<NivelDesempenoResponseDto> findNivelDesempenoById(String id) {
        return nivelDesempenoRepository.findById(id)
                .map(nivel -> modelMapper.map(nivel, NivelDesempenoResponseDto.class));
    }

    @Override
    public List<NivelDesempenoResponseDto> findNivelesDesempenoByCriterioId(String criterioId) {
        // Mejorar: Implementar findByCriterioEvaluacionId(String criterioId) en el repositorio para eficiencia
        return nivelDesempenoRepository.findAll().stream()
                .filter(nivel -> nivel.getCriterioEvaluacion().getId().equals(criterioId))
                .map(nivel -> modelMapper.map(nivel, NivelDesempenoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NivelDesempenoResponseDto updateNivelDesempeno(String id, NivelDesempenoRequestDto nivelDetailsDto) {
        return nivelDesempenoRepository.findById(id).map(nivelExistente -> {
            nivelExistente.setNombre(nivelDetailsDto.getNombre());
            nivelExistente.setDescripcion(nivelDetailsDto.getDescripcion());
            nivelExistente.setNotaMinima(nivelDetailsDto.getNotaMinima());
            nivelExistente.setNotaMaxima(nivelDetailsDto.getNotaMaxima());

            if (nivelExistente.getNotaMinima().compareTo(nivelExistente.getNotaMaxima()) > 0) {
                throw new IllegalArgumentException("La nota mínima no puede ser mayor que la nota máxima.");
            }
            
            NivelDesempeno updatedNivel = nivelDesempenoRepository.save(nivelExistente);
            return modelMapper.map(updatedNivel, NivelDesempenoResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Nivel de desempeño con ID " + id + " no encontrado."));
    }

    @Override
    @Transactional
    public void deleteNivelDesempeno(String id) {
        nivelDesempenoRepository.deleteById(id);
    }
}