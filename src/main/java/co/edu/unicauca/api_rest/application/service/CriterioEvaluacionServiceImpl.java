package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.CriterioEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.CriterioEvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.service.CriterioEvaluacionService;
import co.edu.unicauca.api_rest.domain.model.CriterioEvaluacion;
import co.edu.unicauca.api_rest.domain.model.Rubrica;
import co.edu.unicauca.api_rest.domain.repository.CriterioEvaluacionRepository;
import co.edu.unicauca.api_rest.domain.repository.RubricaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CriterioEvaluacionServiceImpl implements CriterioEvaluacionService {

    private final CriterioEvaluacionRepository criterioEvaluacionRepository;
    private final RubricaRepository rubricaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CriterioEvaluacionServiceImpl(CriterioEvaluacionRepository criterioEvaluacionRepository,
                                        RubricaRepository rubricaRepository,
                                        ModelMapper modelMapper) {
        this.criterioEvaluacionRepository = criterioEvaluacionRepository;
        this.rubricaRepository = rubricaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CriterioEvaluacionResponseDto createCriterio(CriterioEvaluacionRequestDto criterioDto) {
        // Convertir DTO a entidad para la lógica de negocio
        CriterioEvaluacion criterio = modelMapper.map(criterioDto, CriterioEvaluacion.class);

        // Validación de negocio: Asegurar que la rúbrica existe antes de crear el criterio
        Rubrica rubrica = rubricaRepository.findById(criterioDto.getRubricaId())
                .orElseThrow(() -> new RuntimeException("Rúbrica con ID " + criterioDto.getRubricaId() + " no encontrada."));

        criterio.setId(UUID.randomUUID().toString());
        criterio.setRubrica(rubrica); // Asociar la entidad Rubrica cargada
        
        CriterioEvaluacion savedCriterio = criterioEvaluacionRepository.save(criterio);
        return modelMapper.map(savedCriterio, CriterioEvaluacionResponseDto.class); // Convertir entidad a DTO de respuesta
    }

    @Override
    public Optional<CriterioEvaluacionResponseDto> findCriterioById(String id) {
        return criterioEvaluacionRepository.findById(id)
                .map(criterio -> modelMapper.map(criterio, CriterioEvaluacionResponseDto.class));
    }

    @Override
    public List<CriterioEvaluacionResponseDto> findCriteriosByRubricaId(String rubricaId) {
        // Mejorar: Implementar findByRubricaId(String rubricaId) en el repositorio para eficiencia
        return criterioEvaluacionRepository.findAll().stream()
                .filter(criterio -> criterio.getRubrica().getId().equals(rubricaId))
                .map(criterio -> modelMapper.map(criterio, CriterioEvaluacionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CriterioEvaluacionResponseDto updateCriterio(String id, CriterioEvaluacionRequestDto criterioDetailsDto) {
        return criterioEvaluacionRepository.findById(id).map(criterioExistente -> {
            criterioExistente.setDescripcion(criterioDetailsDto.getDescripcion());
            criterioExistente.setPonderacion(criterioDetailsDto.getPonderacion());
            // La rúbrica no se cambia directamente en un update simple.
            CriterioEvaluacion updatedCriterio = criterioEvaluacionRepository.save(criterioExistente);
            return modelMapper.map(updatedCriterio, CriterioEvaluacionResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Criterio de evaluación con ID " + id + " no encontrado."));
    }

    @Override
    @Transactional
    public void deleteCriterio(String id) {
        criterioEvaluacionRepository.deleteById(id);
    }
}