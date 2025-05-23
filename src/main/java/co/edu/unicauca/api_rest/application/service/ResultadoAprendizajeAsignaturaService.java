package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.ResultadoAprendizajeAsignaturaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.ResultadoAprendizajeAsignaturaResponseDto;
import co.edu.unicauca.api_rest.domain.model.ResultadoAprendizajeAsignatura;
import java.util.List;
import java.util.Optional;

public interface ResultadoAprendizajeAsignaturaService {
    List<ResultadoAprendizajeAsignaturaResponseDto> findAllRAAs();
    Optional<ResultadoAprendizajeAsignaturaResponseDto> findRAAById(String id);
    ResultadoAprendizajeAsignaturaResponseDto saveRAA(ResultadoAprendizajeAsignaturaRequestDto raaDto);
    ResultadoAprendizajeAsignaturaResponseDto updateRAA(String id, ResultadoAprendizajeAsignaturaRequestDto raaDetailsDto);
    void deleteRAA(String id);
}
