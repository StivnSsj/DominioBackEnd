package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.domain.model.Evaluacion;
import co.edu.unicauca.api_rest.application.dto.request.DetalleEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.request.EvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.DetalleEvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.dto.response.EvaluacionResponseDto;
import co.edu.unicauca.api_rest.domain.model.DetalleEvaluacion;
import java.util.List;
import java.util.Optional;

public interface EvaluacionService {
    EvaluacionResponseDto createEvaluacion(EvaluacionRequestDto evaluacionDto);
    Optional<EvaluacionResponseDto> findEvaluacionById(String id);
    List<EvaluacionResponseDto> findAllEvaluaciones();
    List<EvaluacionResponseDto> findEvaluacionesByEstudianteId(String estudianteId);
    List<EvaluacionResponseDto> findEvaluacionesByRubricaId(String rubricaId);
    EvaluacionResponseDto updateEvaluacion(String id, EvaluacionRequestDto evaluacionDetailsDto);
    void deleteEvaluacion(String id);
    EvaluacionResponseDto calculateFinalGrade(String evaluacionId);
    DetalleEvaluacionResponseDto addDetalleEvaluacion(String evaluacionId, DetalleEvaluacionRequestDto detalleDto);
    DetalleEvaluacionResponseDto updateDetalleEvaluacion(String evaluacionId, String detalleId, DetalleEvaluacionRequestDto detalleDetailsDto);
    void deleteDetalleEvaluacion(String evaluacionId, String detalleId);
}
