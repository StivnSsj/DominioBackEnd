package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.NivelDesempeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelDesempenoRepository extends JpaRepository<NivelDesempeno, String> {
    // List<NivelDesempeno> findByCriterioEvaluacionId(String criterioId);
}
