package co.edu.unicauca.api_rest.application.controller;

import co.edu.unicauca.api_rest.application.dto.request.CriterioEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.CriterioEvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.service.CriterioEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/criterios")
public class CriterioEvaluacionController {

    private final CriterioEvaluacionService criterioEvaluacionService;

    @Autowired
    public CriterioEvaluacionController(CriterioEvaluacionService criterioEvaluacionService) {
        this.criterioEvaluacionService = criterioEvaluacionService;
    }

    @PostMapping
    public ResponseEntity<CriterioEvaluacionResponseDto> createCriterio(@RequestBody CriterioEvaluacionRequestDto criterioDto) {
        try {
            CriterioEvaluacionResponseDto createdCriterio = criterioEvaluacionService.createCriterio(criterioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCriterio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriterioEvaluacionResponseDto> getCriterioById(@PathVariable String id) {
        Optional<CriterioEvaluacionResponseDto> criterio = criterioEvaluacionService.findCriterioById(id);
        return criterio.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-rubrica/{rubricaId}")
    public ResponseEntity<List<CriterioEvaluacionResponseDto>> getCriteriosByRubricaId(@PathVariable String rubricaId) {
        List<CriterioEvaluacionResponseDto> criterios = criterioEvaluacionService.findCriteriosByRubricaId(rubricaId);
        if (criterios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(criterios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriterioEvaluacionResponseDto> updateCriterio(@PathVariable String id, @RequestBody CriterioEvaluacionRequestDto criterioDto) {
        try {
            CriterioEvaluacionResponseDto updatedCriterio = criterioEvaluacionService.updateCriterio(id, criterioDto);
            return ResponseEntity.ok(updatedCriterio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriterio(@PathVariable String id) {
        try {
            criterioEvaluacionService.deleteCriterio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}