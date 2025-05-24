package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.DetalleEvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.request.EvaluacionRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.DetalleEvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.dto.response.EvaluacionResponseDto;
import co.edu.unicauca.api_rest.application.service.EvaluacionService;
import co.edu.unicauca.api_rest.domain.model.*;
import co.edu.unicauca.api_rest.domain.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final RubricaRepository rubricaRepository;
    private final DetalleEvaluacionRepository detalleEvaluacionRepository;
    private final CriterioEvaluacionRepository criterioEvaluacionRepository;
    private final NivelDesempenoRepository nivelDesempenoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EvaluacionServiceImpl(EvaluacionRepository evaluacionRepository,
                                 RubricaRepository rubricaRepository,
                                 DetalleEvaluacionRepository detalleEvaluacionRepository,
                                 CriterioEvaluacionRepository criterioEvaluacionRepository,
                                 NivelDesempenoRepository nivelDesempenoRepository,
                                 ModelMapper modelMapper) {
        this.evaluacionRepository = evaluacionRepository;
        this.rubricaRepository = rubricaRepository;
        this.detalleEvaluacionRepository = detalleEvaluacionRepository;
        this.criterioEvaluacionRepository = criterioEvaluacionRepository;
        this.nivelDesempenoRepository = nivelDesempenoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public EvaluacionResponseDto createEvaluacion(EvaluacionRequestDto evaluacionDto) {
        Evaluacion evaluacion = modelMapper.map(evaluacionDto, Evaluacion.class);

        Rubrica rubrica = rubricaRepository.findById(evaluacionDto.getRubricaId())
                .orElseThrow(() -> new RuntimeException("Rúbrica con ID " + evaluacionDto.getRubricaId() + " no encontrada."));

        if (!rubrica.getEstaPublicada()) {
            throw new IllegalStateException("La rúbrica con ID " + rubrica.getId() + " no está publicada y no puede ser utilizada para una evaluación.");
        }

        evaluacion.setId(UUID.randomUUID().toString());
        evaluacion.setFechaEvaluacion(OffsetDateTime.now());
        evaluacion.setRubrica(rubrica);
        evaluacion.setNotaFinal(BigDecimal.ZERO);

        Evaluacion savedEvaluacion = evaluacionRepository.save(evaluacion);
        return modelMapper.map(savedEvaluacion, EvaluacionResponseDto.class);
    }

    @Override
    public Optional<EvaluacionResponseDto> findEvaluacionById(String id) {
        return evaluacionRepository.findById(id)
                .map(evaluacion -> modelMapper.map(evaluacion, EvaluacionResponseDto.class));
    }

    @Override
    public List<EvaluacionResponseDto> findAllEvaluaciones() {
        return evaluacionRepository.findAll().stream()
                .map(evaluacion -> modelMapper.map(evaluacion, EvaluacionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluacionResponseDto> findEvaluacionesByEstudianteId(String estudianteId) {
        // Mejorar: Añadir findByEstudianteId al repositorio
        return evaluacionRepository.findAll().stream()
                .filter(e -> e.getEstudianteId().equals(estudianteId))
                .map(evaluacion -> modelMapper.map(evaluacion, EvaluacionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EvaluacionResponseDto> findEvaluacionesByRubricaId(String rubricaId) {
        // Mejorar: Añadir findByRubricaId al repositorio
        return evaluacionRepository.findAll().stream()
                .filter(e -> e.getRubrica().getId().equals(rubricaId))
                .map(evaluacion -> modelMapper.map(evaluacion, EvaluacionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EvaluacionResponseDto updateEvaluacion(String id, EvaluacionRequestDto evaluacionDetailsDto) {
        return evaluacionRepository.findById(id).map(evaluacionExistente -> {
            evaluacionExistente.setRetroalimentacionGeneral(evaluacionDetailsDto.getRetroalimentacionGeneral());
            evaluacionExistente.setEvaluadorId(evaluacionDetailsDto.getEvaluadorId());
            evaluacionExistente.setTipoEvaluador(evaluacionDetailsDto.getTipoEvaluador());
            
            Evaluacion updatedEvaluacion = evaluacionRepository.save(evaluacionExistente);
            return modelMapper.map(updatedEvaluacion, EvaluacionResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Evaluación con ID " + id + " no encontrada."));
    }

    @Override
    @Transactional
    public void deleteEvaluacion(String id) {
        evaluacionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public EvaluacionResponseDto calculateFinalGrade(String evaluacionId) {
        System.out.println("DEBUG: Iniciando cálculo de nota para evaluación con ID: " + evaluacionId);

        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new RuntimeException("Evaluación con ID " + evaluacionId + " no encontrada."));
        System.out.println("DEBUG: Evaluación encontrada: " + evaluacion.getId());


        Rubrica rubrica = rubricaRepository.findById(evaluacion.getRubrica().getId())
                                .orElseThrow(() -> new RuntimeException("Rúbrica asociada a evaluación no encontrada."));

        System.out.println("DEBUG: Rúbrica asociada encontrada: " + rubrica.getNombre() + " (ID: " + rubrica.getId() + ")");
        List<CriterioEvaluacion> criteriosRubrica = criterioEvaluacionRepository.findByRubrica(rubrica);

        System.out.println("DEBUG: Criterios encontrados para la rúbrica: " + criteriosRubrica.size());

        if (criteriosRubrica.isEmpty()) {
            System.out.println("DEBUG ERROR: La rúbrica asociada NO tiene criterios definidos.");
            throw new IllegalStateException("La rúbrica asociada no tiene criterios definidos.");
        }

        BigDecimal notaCalculada = BigDecimal.ZERO;
        BigDecimal sumaPonderacionesCriterios = BigDecimal.ZERO;

        // Recuperar los detalles de la evaluación asociados, asegurándose de que estén cargados
        // Si evaluacion.getDetalles() es Lazy, esta línea los cargará dentro de la transacción
        List<DetalleEvaluacion> detallesEvaluacion = detalleEvaluacionRepository.findByEvaluacion(evaluacion); // Asumo que tienes este método en tu repositorio

        System.out.println("DEBUG: Detalles de evaluación cargados para la evaluación: " + detallesEvaluacion.size());

        for (CriterioEvaluacion criterioRubrica : criteriosRubrica) {
            System.out.println("DEBUG: Procesando criterio: " + criterioRubrica.getDescripcion() + " (ID: " + criterioRubrica.getId() + ")");

            Optional<DetalleEvaluacion> detalleCriterioOpt = detallesEvaluacion.stream()
                    .filter(detalle -> detalle.getCriterioEvaluacion().getId().equals(criterioRubrica.getId()))
                    .findFirst();

            if (detalleCriterioOpt.isEmpty()) {
                System.out.println("DEBUG ERROR: NO se encontró detalle de evaluación para el criterio: " + criterioRubrica.getId());
                throw new IllegalStateException("No se encontró detalle de evaluación para el criterio " + criterioRubrica.getDescripcion());
            }

            DetalleEvaluacion detalleCriterio = detalleCriterioOpt.get();
            System.out.println("DEBUG: Detalle encontrado para el criterio " + criterioRubrica.getId() + ". Nivel seleccionado ID: " + detalleCriterio.getNivelDesempenoSeleccionado().getId());

            NivelDesempeno nivelSeleccionado = nivelDesempenoRepository.findById(detalleCriterio.getNivelDesempenoSeleccionado().getId())
                .orElseThrow(() -> new RuntimeException("Nivel de desempeño seleccionado no encontrado con ID: " + detalleCriterio.getNivelDesempenoSeleccionado().getId()));
            System.out.println("DEBUG: Nivel de desempeño seleccionado cargado: " + nivelSeleccionado.getNombre() + " (Min: " + nivelSeleccionado.getNotaMinima() + ", Max: " + nivelSeleccionado.getNotaMaxima() + ")");

            BigDecimal notaNivel = nivelSeleccionado.getNotaMinima()
                    .add(nivelSeleccionado.getNotaMaxima())
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            System.out.println("DEBUG: Nota promedio del nivel (" + nivelSeleccionado.getNombre() + "): " + notaNivel);


            BigDecimal ponderacion = criterioRubrica.getPonderacion().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            System.out.println("DEBUG: Ponderación del criterio (" + criterioRubrica.getDescripcion() + "): " + ponderacion);

            notaCalculada = notaCalculada.add(notaNivel.multiply(ponderacion));
            sumaPonderacionesCriterios = sumaPonderacionesCriterios.add(criterioRubrica.getPonderacion());
            System.out.println("DEBUG: Nota calculada provisional: " + notaCalculada + ", Suma ponderaciones provisional: " + sumaPonderacionesCriterios);
        }

        System.out.println("DEBUG: Suma final de ponderaciones de criterios: " + sumaPonderacionesCriterios);
        if (sumaPonderacionesCriterios.compareTo(BigDecimal.valueOf(100)) != 0) {
            System.out.println("DEBUG ERROR: La suma de ponderaciones NO es 100. Es: " + sumaPonderacionesCriterios);
            throw new IllegalStateException("La suma de ponderaciones de los criterios de la rúbrica no es 100.");
        }

        evaluacion.setNotaFinal(notaCalculada.setScale(2, RoundingMode.HALF_UP));
        Evaluacion updatedEvaluacion = evaluacionRepository.save(evaluacion);
        System.out.println("DEBUG: Nota final calculada y guardada: " + evaluacion.getNotaFinal());
        return modelMapper.map(updatedEvaluacion, EvaluacionResponseDto.class);
    }

    @Override
    @Transactional
    public DetalleEvaluacionResponseDto addDetalleEvaluacion(String evaluacionId, DetalleEvaluacionRequestDto detalleDto) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new RuntimeException("Evaluación con ID " + evaluacionId + " no encontrada."));

        CriterioEvaluacion criterio = criterioEvaluacionRepository.findById(detalleDto.getCriterioEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Criterio con ID " + detalleDto.getCriterioEvaluacionId() + " no encontrado."));
        NivelDesempeno nivel = nivelDesempenoRepository.findById(detalleDto.getNivelDesempenoSeleccionadoId())
                .orElseThrow(() -> new RuntimeException("Nivel de desempeño con ID " + detalleDto.getNivelDesempenoSeleccionadoId() + " no encontrado."));

        if (!criterio.getRubrica().getId().equals(evaluacion.getRubrica().getId())) {
            throw new IllegalArgumentException("El criterio no pertenece a la rúbrica de esta evaluación.");
        }

        if (!nivel.getCriterioEvaluacion().getId().equals(criterio.getId())) {
            throw new IllegalArgumentException("El nivel de desempeño no pertenece al criterio seleccionado.");
        }

        boolean alreadyExists = evaluacion.getDetalles().stream()
                .anyMatch(d -> d.getCriterioEvaluacion().getId().equals(criterio.getId()));
        if (alreadyExists) {
            throw new IllegalArgumentException("Ya existe un detalle de evaluación para el criterio " + criterio.getDescripcion() + " en esta evaluación.");
        }

        DetalleEvaluacion detalle = modelMapper.map(detalleDto, DetalleEvaluacion.class);
        detalle.setId(UUID.randomUUID().toString());
        detalle.setEvaluacion(evaluacion);
        detalle.setCriterioEvaluacion(criterio);
        detalle.setNivelDesempenoSeleccionado(nivel);

        DetalleEvaluacion savedDetalle = detalleEvaluacionRepository.save(detalle);
        evaluacion.getDetalles().add(savedDetalle); // Asegura que la relación bidireccional se mantenga
        return modelMapper.map(savedDetalle, DetalleEvaluacionResponseDto.class);
    }

    @Override
    @Transactional
    public DetalleEvaluacionResponseDto updateDetalleEvaluacion(String evaluacionId, String detalleId, DetalleEvaluacionRequestDto detalleDetailsDto) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new RuntimeException("Evaluación con ID " + evaluacionId + " no encontrada."));

        return detalleEvaluacionRepository.findById(detalleId).map(detalleExistente -> {
            if (!detalleExistente.getEvaluacion().getId().equals(evaluacionId)) {
                throw new IllegalArgumentException("El detalle de evaluación con ID " + detalleId + " no pertenece a la evaluación con ID " + evaluacionId);
            }

            NivelDesempeno nuevoNivel = nivelDesempenoRepository.findById(detalleDetailsDto.getNivelDesempenoSeleccionadoId())
                    .orElseThrow(() -> new RuntimeException("Nivel de desempeño con ID " + detalleDetailsDto.getNivelDesempenoSeleccionadoId() + " no encontrado."));

            if (!nuevoNivel.getCriterioEvaluacion().getId().equals(detalleExistente.getCriterioEvaluacion().getId())) {
                throw new IllegalArgumentException("El nuevo nivel de desempeño no corresponde al criterio original de este detalle.");
            }

            detalleExistente.setNivelDesempenoSeleccionado(nuevoNivel);
            detalleExistente.setRetroalimentacionEspecifica(detalleDetailsDto.getRetroalimentacionEspecifica());
            
            DetalleEvaluacion updatedDetalle = detalleEvaluacionRepository.save(detalleExistente);
            return modelMapper.map(updatedDetalle, DetalleEvaluacionResponseDto.class);
        }).orElseThrow(() -> new RuntimeException("Detalle de evaluación con ID " + detalleId + " no encontrado."));
    }

    @Override
    @Transactional
    public void deleteDetalleEvaluacion(String evaluacionId, String detalleId) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new RuntimeException("Evaluación con ID " + evaluacionId + " no encontrada."));

        DetalleEvaluacion detalle = detalleEvaluacionRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Detalle de evaluación con ID " + detalleId + " no encontrado."));

        if (!detalle.getEvaluacion().getId().equals(evaluacionId)) {
            throw new IllegalArgumentException("El detalle de evaluación con ID " + detalleId + " no pertenece a la evaluación con ID " + evaluacionId);
        }

        evaluacion.getDetalles().remove(detalle); // Eliminar de la colección de la entidad padre
        detalleEvaluacionRepository.delete(detalle);
    }

}