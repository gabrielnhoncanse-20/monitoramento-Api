package br.com.ecocut.monitoramento.service;

import br.com.ecocut.monitoramento.model.Sensor;
import br.com.ecocut.monitoramento.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository repository;

    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    public List<Sensor> listarTodos() {
        return repository.findAll();
    }

    public Optional<Sensor> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Sensor salvar(Sensor sensor) {
        return repository.save(sensor);
    }

    public Sensor atualizar(Long id, Sensor sensorAtualizado) {

        Sensor sensor = repository.findById(id).orElseThrow();

        sensor.setNome(sensorAtualizado.getNome());
        sensor.setTipo(sensorAtualizado.getTipo());
        sensor.setLocal(sensorAtualizado.getLocal());
        sensor.setUnidade(sensorAtualizado.getUnidade());
        sensor.setLimiteMinimo(sensorAtualizado.getLimiteMinimo());
        sensor.setLimiteMaximo(sensorAtualizado.getLimiteMaximo());
        sensor.setAtivo(sensorAtualizado.getAtivo());

        return repository.save(sensor);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}