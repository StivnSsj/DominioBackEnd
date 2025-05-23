package co.edu.unicauca.api_rest.application.controller;

import co.edu.unicauca.api_rest.application.dto.request.DetalleEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.request.EvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.DetalleEvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.dto.response.EvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @Autowired
    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDto>> getAllEvaluaciones() {
        List<EvaluacionResponseDto> evaluaciones = evaluacionService.findAllEvaluaciones();
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDto> getEvaluacionById(@PathVariable String id) {
        Optional<EvaluacionResponseDto> evaluacion = evaluacionService.findEvaluacionById(id);
        return evaluacion.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EvaluacionResponseDto> createEvaluacion(@RequestBody EvaluacionRequestDto evaluacionDto) {
        try {
            EvaluacionResponseDto createdEvaluacion = evaluacionService.createEvaluacion(evaluacionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDto> updateEvaluacion(@PathVariable String id, @RequestBody EvaluacionRequestDto evaluacionDto) {
        try {
            EvaluacionResponseDto updatedEvaluacion = evaluacionService.updateEvaluacion(id, evaluacionDto);
            return ResponseEntity.ok(updatedEvaluacion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluacion(@PathVariable String id) {
        try {
            evaluacionService.deleteEvaluacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-estudiante/{estudianteId}")
    public ResponseEntity<List<EvaluacionResponseDto>> getEvaluacionesByEstudianteId(@PathVariable String estudianteId) {
        List<EvaluacionResponseDto> evaluaciones = evaluacionService.findEvaluacionesByEstudianteId(estudianteId);
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/by-rubrica/{rubricaId}")
    public ResponseEntity<List<EvaluacionResponseDto>> getEvaluacionesByRubricaId(@PathVariable String rubricaId) {
        List<EvaluacionResponseDto> evaluaciones = evaluacionService.findEvaluacionesByRubricaId(rubricaId);
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(evaluaciones);
    }

    @PostMapping("/{id}/calculate-grade")
    public ResponseEntity<EvaluacionResponseDto> calculateFinalGrade(@PathVariable String id) {
        try {
            EvaluacionResponseDto updatedEvaluacion = evaluacionService.calculateFinalGrade(id);
            return ResponseEntity.ok(updatedEvaluacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{evaluacionId}/detalles")
    public ResponseEntity<DetalleEvaluacionResponseDto> addDetalleEvaluacion(@PathVariable String evaluacionId, @RequestBody DetalleEvaluacionRequestDto detalleDto) {
        try {
            DetalleEvaluacionResponseDto createdDetalle = evaluacionService.addDetalleEvaluacion(evaluacionId, detalleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDetalle);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{evaluacionId}/detalles/{detalleId}")
    public ResponseEntity<DetalleEvaluacionResponseDto> updateDetalleEvaluacion(@PathVariable String evaluacionId, @PathVariable String detalleId, @RequestBody DetalleEvaluacionRequestDto detalleDto) {
        try {
            DetalleEvaluacionResponseDto updatedDetalle = evaluacionService.updateDetalleEvaluacion(evaluacionId, detalleId, detalleDto);
            return ResponseEntity.ok(updatedDetalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{evaluacionId}/detalles/{detalleId}")
    public ResponseEntity<Void> deleteDetalleEvaluacion(@PathVariable String evaluacionId, @PathVariable String detalleId) {
        try {
            evaluacionService.deleteDetalleEvaluacion(evaluacionId, detalleId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}