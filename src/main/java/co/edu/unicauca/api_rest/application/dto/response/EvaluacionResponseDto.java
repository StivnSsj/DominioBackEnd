package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionResponseDto {
    private String id;
    private String rubricaId;
    private String estudianteId;
    private String evaluadorId;
    private String tipoEvaluador;
    private OffsetDateTime fechaEvaluacion;
    private BigDecimal notaFinal;
    private String retroalimentacionGeneral;
    private List<DetalleEvaluacionResponseDto> detalles;
}