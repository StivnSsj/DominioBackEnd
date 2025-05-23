package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleEvaluacionRequestDto {
    private String id; // Nulo para creación
    private String criterioEvaluacionId;
    private String nivelDesempenoSeleccionadoId;
    private String retroalimentacionEspecifica;
    // La evaluacionId viene de la URL del controlador
}