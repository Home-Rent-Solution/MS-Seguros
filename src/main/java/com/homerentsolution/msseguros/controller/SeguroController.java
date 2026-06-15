package com.homerentsolution.msseguros.controller;

import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.service.SeguroService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Listar todos los seguros
    @GetMapping
    public ResponseEntity<List<SeguroResponseDTO>> listar() {

        log.info("Listando todos los seguros");

        return ResponseEntity.ok(
                service.listar()
        );
    }

    // Crear seguro
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

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<SeguroResponseDTO> buscar(
            @PathVariable Long id) {

        log.info(
                "Buscando seguro con ID: {}",
                id
        );

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<SeguroResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SeguroRequestDTO dto) {

        log.info(
                "Actualizando seguro con ID: {}",
                id
        );

        SeguroResponseDTO actualizado =
                service.actualizar(id, dto);

        return ResponseEntity.ok(
                actualizado
        );
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.warn(
                "Eliminando seguro con ID: {}",
                id
        );

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // Buscar por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<SeguroResponseDTO>> buscarPorTipo(
            @PathVariable String tipo) {

        log.info(
                "Buscando seguros del tipo: {}",
                tipo
        );

        return ResponseEntity.ok(
                service.buscarPorTipo(tipo)
        );
    }

    // Buscar por reserva
    @GetMapping("/reserva/{id}")
    public ResponseEntity<List<SeguroResponseDTO>> buscarPorReserva(
            @PathVariable Long id) {

        log.info(
                "Buscando seguros para reserva con ID: {}",
                id
        );

        return ResponseEntity.ok(
                service.buscarPorReserva(id)
        );
    }

    // Buscar ordenados por cobertura
    @GetMapping("/tipo/ordenado/{tipo}")
    public ResponseEntity<List<SeguroResponseDTO>> buscarPorTipoOrdenado(
            @PathVariable String tipo) {

        log.info(
                "Buscando seguros ordenados por cobertura del tipo: {}",
                tipo
        );

        return ResponseEntity.ok(
                service.buscarPorTipoOrdenado(tipo)
        );
    }
}