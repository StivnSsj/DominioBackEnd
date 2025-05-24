package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.DetalleEvaluacion;
import co.edu.unicauca.api_rest.domain.model.Evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleEvaluacionRepository extends JpaRepository<DetalleEvaluacion, String> {

    List<DetalleEvaluacion> findByEvaluacion(Evaluacion evaluacion);
    // List<DetalleEvaluacion> findByEvaluacionId(String evaluacionId);
    // DetalleEvaluacion findByEvaluacionIdAndCriterioEvaluacionId(String evaluacionId, String criterioId);
}
