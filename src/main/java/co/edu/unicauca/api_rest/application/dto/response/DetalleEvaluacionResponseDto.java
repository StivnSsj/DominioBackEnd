package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleEvaluacionResponseDto {
    private String id;
    private String evaluacionId; // Exponemos solo el ID de la evaluación padre
    private String criterioEvaluacionId; // Exponemos solo el ID del criterio
    private String nivelDesempenoSeleccionadoId; // Exponemos solo el ID del nivel
    private String retroalimentacionEspecifica;
}
