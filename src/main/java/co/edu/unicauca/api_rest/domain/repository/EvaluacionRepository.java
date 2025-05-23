package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, String> {
    // List<Evaluacion> findByEstudianteId(String estudianteId);
    // List<Evaluacion> findByRubricaId(String rubricaId);
}
