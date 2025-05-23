package co.edu.unicauca.api_rest.application.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Importaciones para validación (si usas Spring Boot Validation)
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RubricaRequestDto {
    // ID es opcional en el request de creación (POST), requerido en update (PUT)
    private String id; // Puede ser nulo para POST
    
    // @NotBlank(message = "El nombre de la rúbrica es obligatorio")
    // @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
    private String nombre;

    private String resultadoAprendizajeAsignaturaId;
    private String docenteId;
    private Boolean estaPublicada;

    // Nota: fechaCreacion no se envía en el request, se genera en el servicio
    // Ni la lista de criterios, se manejan en endpoints separados
}
