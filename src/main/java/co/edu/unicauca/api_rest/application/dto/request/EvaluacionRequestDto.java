package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionRequestDto {
    private String id; // Nulo para creación
    private String rubricaId;
    private String estudianteId;
    private String evaluadorId;
    private String tipoEvaluador;
    private String retroalimentacionGeneral;
    // NotaFinal y FechaEvaluacion son generados/calculados en el servicio
}