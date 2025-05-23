package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriterioEvaluacionResponseDto {
    private String id;
    private String descripcion;
    private BigDecimal ponderacion;
    private String rubricaId; // Exponemos solo el ID de la rúbrica padre
    private List<NivelDesempenoResponseDto> nivelesDesempeno; // Incluir niveles en la respuesta de criterio
}
