package co.edu.unicauca.api_rest.application.controller;

import co.edu.unicauca.api_rest.application.dto.request.RubricaRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.RubricaResponseDto;
import co.edu.unicauca.api_rest.application.service.RubricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rubricas")
public class RubricaController {

    private final RubricaService rubricaService;

    @Autowired
    public RubricaController(RubricaService rubricaService) {
        this.rubricaService = rubricaService;
    }

    @GetMapping
    public ResponseEntity<List<RubricaResponseDto>> getAllRubricas() { // Cambia el tipo de retorno
        List<RubricaResponseDto> rubricas = rubricaService.findAllRubricas();
        return ResponseEntity.ok(rubricas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RubricaResponseDto> getRubricaById(@PathVariable String id) { // Cambia el tipo de retorno
        Optional<RubricaResponseDto> rubrica = rubricaService.findRubricaById(id);
        return rubrica.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RubricaResponseDto> createRubrica(@RequestBody RubricaRequestDto rubricaDto) { // Recibe Request DTO
        RubricaResponseDto createdRubrica = rubricaService.saveRubrica(rubricaDto); // Pasa Request DTO al servicio
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRubrica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RubricaResponseDto> updateRubrica(@PathVariable String id, @RequestBody RubricaRequestDto rubricaDto) { // Recibe Request DTO
        try {
            RubricaResponseDto updatedRubrica = rubricaService.updateRubrica(id, rubricaDto); // Pasa Request DTO al servicio
            return ResponseEntity.ok(updatedRubrica);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRubrica(@PathVariable String id) {
        try {
            rubricaService.deleteRubrica(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-docente/{docenteId}")
    public ResponseEntity<List<RubricaResponseDto>> getRubricasByDocenteId(@PathVariable String docenteId) { // Cambia el tipo de retorno
        List<RubricaResponseDto> rubricas = rubricaService.findRubricasByDocenteId(docenteId);
        if (rubricas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rubricas);
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<RubricaResponseDto> publishRubrica(@PathVariable String id) { // Cambia el tipo de retorno
        try {
            RubricaResponseDto publishedRubrica = rubricaService.publishRubrica(id);
            return ResponseEntity.ok(publishedRubrica);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}