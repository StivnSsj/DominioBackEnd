package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "detalle_evaluacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleEvaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    // Relación Many-to-One con Evaluacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;

    // Relación Many-to-One con CriterioEvaluacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_evaluacion_id", nullable = false)
    private CriterioEvaluacion criterioEvaluacion;

    // Relación Many-to-One con NivelDesempeno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nivel_desempeno_seleccionado_id", nullable = false)
    private NivelDesempeno nivelDesempenoSeleccionado;

    @Column(name = "retroalimentacion_especifica", length = 1000)
    private String retroalimentacionEspecifica;
}
