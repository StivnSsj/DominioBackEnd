package co.edu.unicauca.api_rest.application.service;

import co.edu.unicauca.api_rest.application.dto.request.NivelDesempenoRequestDto;
import co.edu.unicauca.api_rest.application.dto.response.NivelDesempenoResponseDto;
import co.edu.unicauca.api_rest.domain.model.NivelDesempeno;
import java.util.List;
import java.util.Optional;

public interface NivelDesempenoService {
    NivelDesempenoResponseDto createNivelDesempeno(NivelDesempenoRequestDto nivelDesempenoDto);
    Optional<NivelDesempenoResponseDto> findNivelDesempenoById(String id);
    List<NivelDesempenoResponseDto> findNivelesDesempenoByCriterioId(String criterioId);
    NivelDesempenoResponseDto updateNivelDesempeno(String id, NivelDesempenoRequestDto nivelDetailsDto);
    void deleteNivelDesempeno(String id);
}
