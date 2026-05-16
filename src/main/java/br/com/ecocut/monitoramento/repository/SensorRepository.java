package br.com.ecocut.monitoramento.repository;

import br.com.ecocut.monitoramento.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}