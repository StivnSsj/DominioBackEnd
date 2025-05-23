package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoAprendizajeAsignaturaRequestDto {
    private String id; // Nulo para creación
    private String descripcion;
    private String asignaturaId;
    private String resultadoAprendizajeProgramaId;
    private String competenciaProgramaId;
    private String docenteId;
    private String periodoAcademico;
}
