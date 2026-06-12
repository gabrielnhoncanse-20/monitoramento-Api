package br.com.ecocut.monitoramento.repository;

import br.com.ecocut.monitoramento.model.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    List<Medicao> findBySensorId(Long sensorId);
}