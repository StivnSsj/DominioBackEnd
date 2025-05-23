package co.edu.unicauca.api_rest.application.controller;

import co.edu.unicauca.api_rest.application.dto.request.ResultadoAprendizajeAsignaturaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.ResultadoAprendizajeAsignaturaResponseDto;
import co.edu.unicauca.api_rest.application.service.ResultadoAprendizajeAsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/raas")
public class ResultadoAprendizajeAsignaturaController {

    private final ResultadoAprendizajeAsignaturaService raaService;

    @Autowired
    public ResultadoAprendizajeAsignaturaController(ResultadoAprendizajeAsignaturaService raaService) {
        this.raaService = raaService;
    }

    @GetMapping
    public ResponseEntity<List<ResultadoAprendizajeAsignaturaResponseDto>> getAllRAAs() {
        List<ResultadoAprendizajeAsignaturaResponseDto> raas = raaService.findAllRAAs();
        return ResponseEntity.ok(raas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoAprendizajeAsignaturaResponseDto> getRAAById(@PathVariable String id) {
        Optional<ResultadoAprendizajeAsignaturaResponseDto> raa = raaService.findRAAById(id);
        return raa.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResultadoAprendizajeAsignaturaResponseDto> createRAA(@RequestBody ResultadoAprendizajeAsignaturaRequestDto raaDto) {
        ResultadoAprendizajeAsignaturaResponseDto createdRAA = raaService.saveRAA(raaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRAA);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoAprendizajeAsignaturaResponseDto> updateRAA(@PathVariable String id, @RequestBody ResultadoAprendizajeAsignaturaRequestDto raaDto) {
        try {
            ResultadoAprendizajeAsignaturaResponseDto updatedRAA = raaService.updateRAA(id, raaDto);
            return ResponseEntity.ok(updatedRAA);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRAA(@PathVariable String id) {
        try {
            raaService.deleteRAA(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}