package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.CriterioEvaluacion;
import co.edu.unicauca.api_rest.domain.model.Rubrica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriterioEvaluacionRepository extends JpaRepository<CriterioEvaluacion, String> {

    // Nuevo método para encontrar criterios por su rúbrica asociada
    List<CriterioEvaluacion> findByRubrica(Rubrica rubrica);
    //List<CriterioEvaluacion> findByRubricaId(String rubricaId);
}
