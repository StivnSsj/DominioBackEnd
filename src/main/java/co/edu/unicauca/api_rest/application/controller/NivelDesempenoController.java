package co.edu.unicauca.api_rest.application.controller;

import co.edu.unicauca.api_rest.application.dto.request.NivelDesempenoRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.NivelDesempenoResponseDto;
import co.edu.unicauca.api_rest.application.service.NivelDesempenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/niveles-desempeno")
public class NivelDesempenoController {

    private final NivelDesempenoService nivelDesempenoService;

    @Autowired
    public NivelDesempenoController(NivelDesempenoService nivelDesempenoService) {
        this.nivelDesempenoService = nivelDesempenoService;
    }

    @PostMapping
    public ResponseEntity<NivelDesempenoResponseDto> createNivelDesempeno(@RequestBody NivelDesempenoRequestDto nivelDesempenoDto) {
        try {
            NivelDesempenoResponseDto createdNivel = nivelDesempenoService.createNivelDesempeno(nivelDesempenoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNivel);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelDesempenoResponseDto> getNivelDesempenoById(@PathVariable String id) {
        Optional<NivelDesempenoResponseDto> nivel = nivelDesempenoService.findNivelDesempenoById(id);
        return nivel.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-criterio/{criterioId}")
    public ResponseEntity<List<NivelDesempenoResponseDto>> getNivelesDesempenoByCriterioId(@PathVariable String criterioId) {
        List<NivelDesempenoResponseDto> niveles = nivelDesempenoService.findNivelesDesempenoByCriterioId(criterioId);
        if (niveles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(niveles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelDesempenoResponseDto> updateNivelDesempeno(@PathVariable String id, @RequestBody NivelDesempenoRequestDto nivelDesempenoDto) {
        try {
            NivelDesempenoResponseDto updatedNivel = nivelDesempenoService.updateNivelDesempeno(id, nivelDesempenoDto);
            return ResponseEntity.ok(updatedNivel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNivelDesempeno(@PathVariable String id) {
        try {
            nivelDesempenoService.deleteNivelDesempeno(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}