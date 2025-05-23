package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.CriterioEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.CriterioEvaluacionResponseDto;
import co.edu.unicauca.api_rest.domain.model.CriterioEvaluacion;
import java.util.List;
import java.util.Optional;

public interface CriterioEvaluacionService {
    CriterioEvaluacionResponseDto createCriterio(CriterioEvaluacionRequestDto criterioDto);
    Optional<CriterioEvaluacionResponseDto> findCriterioById(String id);
    List<CriterioEvaluacionResponseDto> findCriteriosByRubricaId(String rubricaId);
    CriterioEvaluacionResponseDto updateCriterio(String id, CriterioEvaluacionRequestDto criterioDetailsDto);
    void deleteCriterio(String id);
}
