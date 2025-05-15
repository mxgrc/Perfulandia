package com.Perfulandia.perfulandia.envio.controller;

import com.Perfulandia.perfulandia.envio.model.Envio;
import com.Perfulandia.perfulandia.envio.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listar() {
        List<Envio> envios = envioService.findAll();
        return envios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(envios);
    }

    @PostMapping
    public ResponseEntity<Envio> guardar(@RequestBody Envio envio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.save(envio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscar(@PathVariable Long id) {
        Envio envio = envioService.findById(id);
        return (envio != null) ? ResponseEntity.ok(envio) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizar(@PathVariable Long id, @RequestBody Envio envio) {
        Envio existente = envioService.findById(id);
        if (existente != null) {
            existente.setDireccion(envio.getDireccion());
            existente.setEstado(envio.getEstado());
            existente.setFechaEnvio(envio.getFechaEnvio());
            return ResponseEntity.ok(envioService.save(existente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Envio envio = envioService.findById(id);
        if (envio != null) {
            envioService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
