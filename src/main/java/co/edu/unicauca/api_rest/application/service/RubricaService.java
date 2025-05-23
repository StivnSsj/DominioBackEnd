package co.edu.unicauca.api_rest.application.service;


import java.util.List;
import java.util.Optional;

import co.edu.unicauca.api_rest.application.dto.request.RubricaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.RubricaResponseDto;

public interface RubricaService {
    List<RubricaResponseDto> findAllRubricas(); // Cambia a DTO de respuesta
    Optional<RubricaResponseDto> findRubricaById(String id); // Cambia a DTO de respuesta
    RubricaResponseDto saveRubrica(RubricaRequestDto rubricaDto); // Recibe DTO de request, retorna DTO de respuesta
    RubricaResponseDto updateRubrica(String id, RubricaRequestDto rubricaDto); // Recibe DTO de request, retorna DTO de respuesta
    void deleteRubrica(String id);
    List<RubricaResponseDto> findRubricasByResultadoAprendizajeAsignaturaId(String raaId);
    List<RubricaResponseDto> findRubricasByDocenteId(String docenteId);
    RubricaResponseDto publishRubrica(String id);
}
