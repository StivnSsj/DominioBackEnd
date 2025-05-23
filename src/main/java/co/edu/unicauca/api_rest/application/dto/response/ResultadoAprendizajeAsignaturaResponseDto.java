package co.edu.unicauca.api_rest.application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoAprendizajeAsignaturaResponseDto {
    private String id;
    private String descripcion;
    private String asignaturaId;
    private String resultadoAprendizajeProgramaId;
    private String competenciaProgramaId;
    private String docenteId;
    private String periodoAcademico;
}
