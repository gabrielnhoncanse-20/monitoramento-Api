package br.com.ecocut.monitoramento.service;

import br.com.ecocut.monitoramento.dto.MedicaoResponseDTO;
import br.com.ecocut.monitoramento.model.Medicao;
import br.com.ecocut.monitoramento.model.Sensor;
import br.com.ecocut.monitoramento.model.StatusMedicao;
import br.com.ecocut.monitoramento.repository.MedicaoRepository;
import br.com.ecocut.monitoramento.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MedicaoService {

    private final MedicaoRepository medicaoRepository;
    private final SensorRepository sensorRepository;
    private final Random random = new Random();

    public MedicaoService(MedicaoRepository medicaoRepository, SensorRepository sensorRepository) {
        this.medicaoRepository = medicaoRepository;
        this.sensorRepository = sensorRepository;
    }

    public MedicaoResponseDTO registrarMedicao(Long sensorId, Double valor) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        Medicao medicao = new Medicao();
        medicao.setSensor(sensor);
        medicao.setValor(valor);
        medicao.setData(LocalDateTime.now());

        Medicao salva = medicaoRepository.save(medicao);
        return converterParaDTO(salva);
    }

    public List<MedicaoResponseDTO> listarTodas() {
        return medicaoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public MedicaoResponseDTO buscarPorId(Long id) {
        Medicao medicao = medicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medição não encontrada"));
        return converterParaDTO(medicao);
    }

    public List<MedicaoResponseDTO> buscarPorSensor(Long sensorId) {
        return medicaoRepository.findBySensorId(sensorId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public MedicaoResponseDTO simularMedicaoAleatoria() {
        List<Sensor> sensores = sensorRepository.findAll();

        if (sensores.isEmpty()) {
            throw new RuntimeException("Nenhum sensor cadastrado");
        }

        Sensor sensor = sensores.get(random.nextInt(sensores.size()));

        double minimo = sensor.getLimiteMinimo() != null ? sensor.getLimiteMinimo() : 0.0;
        double maximo = sensor.getLimiteMaximo() != null ? sensor.getLimiteMaximo() : 100.0;

        double valor = minimo + (maximo - minimo) * random.nextDouble();

        return registrarMedicao(sensor.getId(), valor);
    }

    public StatusMedicao calcularStatus(Double valor, Sensor sensor) {
        Double min = sensor.getLimiteMinimo();
        Double max = sensor.getLimiteMaximo();

        if (min == null || max == null) {
            return StatusMedicao.NORMAL;
        }

        if (valor < min || valor > max) {
            return StatusMedicao.CRITICO;
        }

        Double margem = (max - min) * 0.1;

        if (valor <= (min + margem) || valor >= (max - margem)) {
            return StatusMedicao.ALERTA;
        }

        return StatusMedicao.NORMAL;
    }

    private MedicaoResponseDTO converterParaDTO(Medicao m) {
        StatusMedicao status = calcularStatus(m.getValor(), m.getSensor());

        return new MedicaoResponseDTO(
                m.getId(),
                m.getSensor().getId(),
                m.getSensor().getNome(),
                m.getSensor().getTipo(),
                m.getSensor().getUnidade(),
                m.getValor(),
                m.getData(),
                status
        );
    }
}