package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.RubricaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.RubricaResponseDto;
import co.edu.unicauca.api_rest.domain.model.Rubrica;
import co.edu.unicauca.api_rest.domain.repository.RubricaRepository;
import org.modelmapper.ModelMapper; // Importa ModelMapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RubricaServiceImpl implements RubricaService {

    private final RubricaRepository rubricaRepository;
    private final ModelMapper modelMapper; // Inyecta ModelMapper

    @Autowired
    public RubricaServiceImpl(RubricaRepository rubricaRepository, ModelMapper modelMapper) {
        this.rubricaRepository = rubricaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RubricaResponseDto> findAllRubricas() {
        return rubricaRepository.findAll().stream()
                .map(rubrica -> modelMapper.map(rubrica, RubricaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RubricaResponseDto> findRubricaById(String id) {
        return rubricaRepository.findById(id)
                .map(rubrica -> modelMapper.map(rubrica, RubricaResponseDto.class));
    }

    @Override
    @Transactional
    public RubricaResponseDto saveRubrica(RubricaRequestDto rubricaDto) {
        Rubrica rubrica = modelMapper.map(rubricaDto, Rubrica.class); // Convierte DTO a Entidad

        if (rubrica.getId() == null || rubrica.getId().isEmpty()) {
            rubrica.setId(UUID.randomUUID().toString());
        }
        if (rubrica.getFechaCreacion() == null) {
            rubrica.setFechaCreacion(OffsetDateTime.now());
        }
        // Asegúrate de que las relaciones (criterios) no se manejen directamente aquí
        // si los estás creando por separado
        Rubrica savedRubrica = rubricaRepository.save(rubrica);
        return modelMapper.map(savedRubrica, RubricaResponseDto.class); // Convierte Entidad a DTO de respuesta
    }

    @Override
    @Transactional
    public RubricaResponseDto updateRubrica(String id, RubricaRequestDto rubricaDto) {
        return rubricaRepository.findById(id).map(rubricaExistente -> {
            // Actualiza solo los campos permitidos desde el DTO
            rubricaExistente.setNombre(rubricaDto.getNombre());
            rubricaExistente.setResultadoAprendizajeAsignaturaId(rubricaDto.getResultadoAprendizajeAsignaturaId());
            rubricaExistente.setDocenteId(rubricaDto.getDocenteId());
            rubricaExistente.setEstaPublicada(rubricaDto.getEstaPublicada());

            Rubrica updatedRubrica = rubricaRepository.save(rubricaExistente);
            return modelMapper.map(updatedRubrica, RubricaResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Rúbrica no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public void deleteRubrica(String id) {
        rubricaRepository.deleteById(id);
    }

    @Override
    public List<RubricaResponseDto> findRubricasByResultadoAprendizajeAsignaturaId(String raaId) {
        // Asumiendo que has añadido el método al repositorio
        // return rubricaRepository.findByResultadoAprendizajeAsignaturaId(raaId).stream()
        return rubricaRepository.findAll().stream() // Simulación si no tienes el método en repo
                .filter(r -> r.getResultadoAprendizajeAsignaturaId().equals(raaId))
                .map(rubrica -> modelMapper.map(rubrica, RubricaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RubricaResponseDto> findRubricasByDocenteId(String docenteId) {
        // Asumiendo que has añadido el método al repositorio
        // return rubricaRepository.findByDocenteId(docenteId).stream()
        return rubricaRepository.findAll().stream() // Simulación si no tienes el método en repo
                .filter(r -> r.getDocenteId().equals(docenteId))
                .map(rubrica -> modelMapper.map(rubrica, RubricaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RubricaResponseDto publishRubrica(String id) {
        return rubricaRepository.findById(id).map(rubricaExistente -> {
            rubricaExistente.setEstaPublicada(true);
            Rubrica publishedRubrica = rubricaRepository.save(rubricaExistente);
            return modelMapper.map(publishedRubrica, RubricaResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Rúbrica no encontrada con ID: " + id));
    }
}