package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.Rubrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricaRepository extends JpaRepository<Rubrica, String> {
    // Métodos personalizados para buscar rúbricas por RAA o docente
    // List<Rubrica> findByResultadoAprendizajeAsignaturaId(String raaId);
    // List<Rubrica> findByDocenteId(String docenteId);
}
