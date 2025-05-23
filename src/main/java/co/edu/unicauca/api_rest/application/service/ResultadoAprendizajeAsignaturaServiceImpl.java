package co.edu.unicauca.api_rest.application.service;


import co.edu.unicauca.api_rest.application.dto.request.ResultadoAprendizajeAsignaturaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.ResultadoAprendizajeAsignaturaResponseDto;
import co.edu.unicauca.api_rest.domain.model.ResultadoAprendizajeAsignatura;
import co.edu.unicauca.api_rest.domain.repository.ResultadoAprendizajeAsignaturaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResultadoAprendizajeAsignaturaServiceImpl implements ResultadoAprendizajeAsignaturaService {

    private final ResultadoAprendizajeAsignaturaRepository raaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultadoAprendizajeAsignaturaServiceImpl(ResultadoAprendizajeAsignaturaRepository raaRepository, ModelMapper modelMapper) {
        this.raaRepository = raaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ResultadoAprendizajeAsignaturaResponseDto> findAllRAAs() {
        return raaRepository.findAll().stream()
                .map(raa -> modelMapper.map(raa, ResultadoAprendizajeAsignaturaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResultadoAprendizajeAsignaturaResponseDto> findRAAById(String id) {
        return raaRepository.findById(id)
                .map(raa -> modelMapper.map(raa, ResultadoAprendizajeAsignaturaResponseDto.class));
    }

    @Override
    @Transactional
    public ResultadoAprendizajeAsignaturaResponseDto saveRAA(ResultadoAprendizajeAsignaturaRequestDto raaDto) {
        ResultadoAprendizajeAsignatura raa = modelMapper.map(raaDto, ResultadoAprendizajeAsignatura.class);
        if (raa.getId() == null || raa.getId().isEmpty()) {
            raa.setId(UUID.randomUUID().toString());
        }
        ResultadoAprendizajeAsignatura savedRaa = raaRepository.save(raa);
        return modelMapper.map(savedRaa, ResultadoAprendizajeAsignaturaResponseDto.class);
    }

    @Override
    @Transactional
    public ResultadoAprendizajeAsignaturaResponseDto updateRAA(String id, ResultadoAprendizajeAsignaturaRequestDto raaDetailsDto) {
        return raaRepository.findById(id).map(existingRaa -> {
            existingRaa.setDescripcion(raaDetailsDto.getDescripcion());
            existingRaa.setAsignaturaId(raaDetailsDto.getAsignaturaId());
            existingRaa.setResultadoAprendizajeProgramaId(raaDetailsDto.getResultadoAprendizajeProgramaId());
            existingRaa.setCompetenciaProgramaId(raaDetailsDto.getCompetenciaProgramaId());
            existingRaa.setDocenteId(raaDetailsDto.getDocenteId());
            existingRaa.setPeriodoAcademico(raaDetailsDto.getPeriodoAcademico());
            
            ResultadoAprendizajeAsignatura updatedRaa = raaRepository.save(existingRaa);
            return modelMapper.map(updatedRaa, ResultadoAprendizajeAsignaturaResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("RAA no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void deleteRAA(String id) {
        raaRepository.deleteById(id);
    }
}