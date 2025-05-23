package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "evaluacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    // Relación Many-to-One con Rubrica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubrica_id", nullable = false)
    private Rubrica rubrica;

    @Column(name = "estudiante_id", nullable = false)
    private String estudianteId; // ID del estudiante (Mock)

    @Column(name = "evaluador_id", nullable = false)
    private String evaluadorId; // ID del evaluador (puede ser Docente o EvaluadorExterno)

    @Column(name = "tipo_evaluador", nullable = false, length = 50)
    private String tipoEvaluador; // 'DOCENTE' o 'EVALUADOR_EXTERNO'

    @Column(name = "fecha_evaluacion", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime fechaEvaluacion;

    @Column(name = "nota_final", precision = 4, scale = 2)
    private BigDecimal notaFinal;

    @Column(name = "retroalimentacion_general", length = 1000)
    private String retroalimentacionGeneral;

    // Relación One-to-Many con DetalleEvaluacion
    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleEvaluacion> detalles;
}
