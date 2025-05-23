package co.edu.unicauca.api_rest.domain.repository;

import co.edu.unicauca.api_rest.domain.model.ResultadoAprendizajeAsignatura; // Asegúrate de usar el paquete correcto
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, pero buena práctica para indicar que es un componente de Spring
public interface ResultadoAprendizajeAsignaturaRepository extends JpaRepository<ResultadoAprendizajeAsignatura, String> {
    // JpaRepository<TipoDeEntidad, TipoDeId>
    // Spring Data JPA automáticamente provee métodos CRUD (save, findById, findAll, delete, etc.)
    // Puedes añadir métodos personalizados aquí si necesitas consultas más específicas,
    // por ejemplo:
    // List<ResultadoAprendizajeAsignatura> findByDocenteId(String docenteId);
}
