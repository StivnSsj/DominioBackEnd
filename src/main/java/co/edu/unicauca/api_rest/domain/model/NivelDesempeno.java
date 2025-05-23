package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "nivel_desempeno")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NivelDesempeno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "nota_minima", nullable = false, precision = 4, scale = 2)
    private BigDecimal notaMinima;

    @Column(name = "nota_maxima", nullable = false, precision = 4, scale = 2)
    private BigDecimal notaMaxima;

    // Relación Many-to-One con CriterioEvaluacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_evaluacion_id", nullable = false)
    private CriterioEvaluacion criterioEvaluacion;
}
