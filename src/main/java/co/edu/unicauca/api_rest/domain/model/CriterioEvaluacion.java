package co.edu.unicauca.api_rest.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "criterio_evaluacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriterioEvaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "ponderacion", nullable = false, precision = 5, scale = 2)
    private BigDecimal ponderacion; // BigDecimal para precisión en valores monetarios/porcentajes

    // Relación Many-to-One con Rubrica
    // @JoinColumn indica la columna de la clave externa en esta tabla (criterio_evaluacion)
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading para evitar cargar la rúbrica si no es necesaria
    @JoinColumn(name = "rubrica_id", nullable = false)
    private Rubrica rubrica;

    // Relación One-to-Many con NivelDesempeno
    @OneToMany(mappedBy = "criterioEvaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NivelDesempeno> nivelesDesempeno;
}