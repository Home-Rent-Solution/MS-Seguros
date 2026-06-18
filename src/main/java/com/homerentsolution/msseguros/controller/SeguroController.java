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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
// Agrupa todos los endpoints de seguros en una seccion de Swagger
@Tag(
        name = "Seguros",
        description = "Operaciones relacionadas con seguros"
)
@RestController
@RequestMapping("/api/v1/seguros")
public class SeguroController {

    @Autowired
    private SeguroService service;

    private static final Logger log =
            LoggerFactory.getLogger(SeguroController.class);


    // Documenta el endpoint en Swagger
    @Operation(
            summary = "Listar seguros",
            description = "Obtiene todos los seguros registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguros obtenidos correctamente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    // Listar todos los seguros
    @GetMapping
    public ResponseEntity<List<SeguroResponseDTO>> listar() {

        log.info("Listando todos los seguros");

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @Operation(
            summary = "Crear seguro",
            description = "Registra un nuevo seguro"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Seguro creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
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

    @Operation(
            summary = "Buscar seguro",
            description = "Obtiene un seguro por su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguro encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seguro no encontrado"
            )
    })
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

    @Operation(
            summary = "Actualizar seguro",
            description = "Actualiza un seguro existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguro actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seguro no encontrado"
            )
    })
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

    @Operation(
            summary = "Eliminar seguro",
            description = "Elimina un seguro existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Seguro eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Seguro no encontrado"
            )
    })
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

    @Operation(
            summary = "Buscar por tipo",
            description = "Obtiene los seguros asociados a un tipo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguros encontrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen seguros para el tipo indicado"
            )
    })
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


    @Operation(
            summary = "Buscar por reserva",
            description = "Obtiene los seguros asociados a una reserva"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguros encontrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen seguros para la reserva indicada"
            )
    })
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

    @Operation(
            summary = "Buscar seguros ordenados",
            description = "Obtiene los seguros ordenados por cobertura descendente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Seguros encontrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existen seguros para el tipo indicado"
            )
    })
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