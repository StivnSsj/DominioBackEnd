package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List; // Para incluir criterios, si lo deseas en la respuesta

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RubricaResponseDto {
    private String id;
    private String nombre;
    private OffsetDateTime fechaCreacion;
    private String resultadoAprendizajeAsignaturaId;
    private String docenteId;
    private Boolean estaPublicada;
    private List<CriterioEvaluacionResponseDto> criterios; // Incluir criterios en la respuesta de rúbrica
}
