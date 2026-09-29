package br.com.ecocut.monitoramento.controller;

import br.com.ecocut.monitoramento.model.Sensor;
import br.com.ecocut.monitoramento.service.SensorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensores")
@CrossOrigin(origins = "*")
public class SensorController {

    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sensor> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Sensor buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElseThrow();
    }

    @PostMapping
    public Sensor criar(@RequestBody Sensor sensor) {
        return service.salvar(sensor);
    }

    @PutMapping("/{id}")
    public Sensor atualizar(
            @PathVariable Long id,
            @RequestBody Sensor sensor
    ) {
        return service.atualizar(id, sensor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}