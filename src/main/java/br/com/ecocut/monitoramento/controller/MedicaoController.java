package br.com.ecocut.monitoramento.controller;

import br.com.ecocut.monitoramento.dto.MedicaoResponseDTO;
import br.com.ecocut.monitoramento.service.MedicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicoes")
@CrossOrigin(origins = "*")
public class MedicaoController {

    private final MedicaoService medicaoService;

    public MedicaoController(MedicaoService medicaoService) {
        this.medicaoService = medicaoService;
    }

    @PostMapping
    public ResponseEntity<MedicaoResponseDTO> criar(@RequestBody MedicaoRequest request) {
        MedicaoResponseDTO dto = medicaoService.registrarMedicao(request.sensorId(), request.valor());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/simular")
    public ResponseEntity<MedicaoResponseDTO> simular() {
        return ResponseEntity.ok(medicaoService.simularMedicaoAleatoria());
    }

    @GetMapping
    public ResponseEntity<List<MedicaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(medicaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicaoService.buscarPorId(id));
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<List<MedicaoResponseDTO>> buscarPorSensor(@PathVariable Long id) {
        return ResponseEntity.ok(medicaoService.buscarPorSensor(id));
    }
}

record MedicaoRequest(Long sensorId, Double valor) {}