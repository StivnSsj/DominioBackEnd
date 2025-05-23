package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.OffsetDateTime; // O LocalDateTime, OffsetDateTime es mejor para zonas horarias
import java.util.List;

@Entity
@Table(name = "rubrica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rubrica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime fechaCreacion; // Usamos OffsetDateTime para manejar la zona horaria

    @Column(name = "resultado_aprendizaje_asignatura_id", nullable = false)
    private String resultadoAprendizajeAsignaturaId; // ID del RAA al que pertenece la rúbrica

    @Column(name = "docente_id", nullable = false)
    private String docenteId; // ID del docente que crea la rúbrica

    @Column(name = "esta_publicada")
    private Boolean estaPublicada;

    // Relación One-to-Many con CriterioEvaluacion
    // mappedBy indica que la relación es gestionada por el campo 'rubrica' en CriterioEvaluacion
    // CascadeType.ALL significa que operaciones (persist, remove) se propagarán a los criterios
    // orphanRemoval = true: si un criterio se desvincula de la lista, se elimina de la DB
    @OneToMany(mappedBy = "rubrica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriterioEvaluacion> criterios;
}