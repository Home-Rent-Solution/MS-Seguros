package com.homerentsolution.msseguros.controller;

import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.model.Seguro;
import com.homerentsolution.msseguros.service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seguros")
public class SeguroController {

    @Autowired
    private SeguroService service;

    private static final Logger log =
            LoggerFactory.getLogger(SeguroController.class);

    //buscar la lista completa, ResponseEntity responde 200 OK
    @GetMapping
    public ResponseEntity<List<Seguro>> listar() {

        log.info("Listando todos los seguros");

        return ResponseEntity.ok(
                service.listar());
    }

    //crear datos en dto,ResponseEntity devuelve 201 created
    @PostMapping
    public ResponseEntity<SeguroResponseDTO> guardar(
            @Valid @RequestBody SeguroRequestDTO dto) {

        log.info(
                "Guardando seguro tipo {} para reserva {}",
                dto.getTipo(),
                dto.getIdReserva()
        );

        SeguroResponseDTO respuesta =
                service.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    //buscar por Id,ResponseEntity responde HTTP 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<Seguro> buscar(
            @PathVariable Long id) {
        log.info("Buscando seguro con ID: {}", id);

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    // actualizar por Id, ResponseEntity devuelve respuesta HTTP 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<SeguroResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SeguroRequestDTO dto) {

        log.info("Actualizando seguro con ID: {}", id);

        SeguroResponseDTO actualizado =
                service.actualizar(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    //eliminar seguro por Id, ResponseEntity devuelve respuesta HTTP 204 NO CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.warn("Eliminando seguro con ID: {}", id);

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // Buscar seguros por tipo,ResponseEntity devuelve respuesta HTTP 200 OK
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Seguro>> buscarPorTipo(
            @PathVariable String tipo) {

        log.info("Buscando seguros del tipo: {}", tipo);

        return ResponseEntity.ok(
                service.buscarPorTipo(tipo));
    }

    // Buscar seguros por reserva, ResponseEntity devuelve respuesta HTTP 200 OK
    @GetMapping("/reserva/{id}")
    public ResponseEntity<List<Seguro>> buscarPorReserva(
            @PathVariable Long id) {

        log.info("Buscando seguros para reserva con ID: {}", id);

        return ResponseEntity.ok(
                service.buscarPorReserva(id));
    }

    // Buscar seguros ordenados por cobertura según tipo, ResponseEntity responde 200 OK
    @GetMapping("/tipo/ordenado/{tipo}")
    public ResponseEntity<List<Seguro>> buscarPorTipoOrdenado(
            @PathVariable String tipo) {

        log.info("Buscando seguros ordenados por cobertura del tipo: {}", tipo);

        return ResponseEntity.ok(
                service.buscarPorTipoOrdenado(tipo));
    }
}
