package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriterioEvaluacionRequestDto {
    private String id; // Nulo para creación
    private String descripcion;
    private BigDecimal ponderacion;
    private String rubricaId; // Solo el ID de la rúbrica padre
}
