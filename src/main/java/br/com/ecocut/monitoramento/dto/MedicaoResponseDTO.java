package br.com.ecocut.monitoramento.dto;

import br.com.ecocut.monitoramento.model.StatusMedicao;
import java.time.LocalDateTime;

public record MedicaoResponseDTO(
        Long id,
        Long sensorId,
        String sensorNome,
        String sensorTipo,
        String sensorUnidade,
        Double valor,
        LocalDateTime data,
        StatusMedicao status
) {}