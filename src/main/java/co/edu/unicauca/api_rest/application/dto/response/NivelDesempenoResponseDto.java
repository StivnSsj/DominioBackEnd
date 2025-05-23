package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelDesempenoResponseDto {
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal notaMinima;
    private BigDecimal notaMaxima;
    private String criterioEvaluacionId; // Exponemos solo el ID del criterio padre
}