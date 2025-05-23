package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "resultado_aprendizaje_asignatura")
@Data // Genera getters, setters, toString, equals, hashCode
@AllArgsConstructor // Genera un constructor con todos los argumentos
@NoArgsConstructor // Genera un constructor sin argumentos
public class ResultadoAprendizajeAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    // @GeneratedValue(strategy = GenerationType.UUID) // Podríamos usar UUID, pero por ahora se asume que lo pones manual o desde data.sql
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "asignatura_id", nullable = false)
    private String asignaturaId; // ID de la asignatura (Mock)

    @Column(name = "resultado_aprendizaje_programa_id", nullable = false)
    private String resultadoAprendizajeProgramaId; // ID del RA del programa (Mock)

    @Column(name = "competencia_programa_id", nullable = false)
    private String competenciaProgramaId; // ID de la competencia del programa (Mock)

    @Column(name = "docente_id", nullable = false)
    private String docenteId; // ID del docente (Mock)

    @Column(name = "periodo_academico", nullable = false, length = 50)
    private String periodoAcademico;

    // Relaciones (no se mapean como objetos por ahora, se usan solo los IDs)
    // En un sistema real, estas serían @ManyToOne a entidades Asignatura, Docente, etc.
}
