package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.DetalleEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEvaluacionRepository extends JpaRepository<DetalleEvaluacion, String> {
    // List<DetalleEvaluacion> findByEvaluacionId(String evaluacionId);
    // DetalleEvaluacion findByEvaluacionIdAndCriterioEvaluacionId(String evaluacionId, String criterioId);
}
