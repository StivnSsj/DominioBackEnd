package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelDesempenoRequestDto {
    private String id; // Nulo para creación
    private String nombre;
    private String descripcion;
    private BigDecimal notaMinima;
    private BigDecimal notaMaxima;
    private String criterioEvaluacionId; // Solo el ID del criterio padre
}
