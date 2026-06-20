package com.homerentsolution.msseguros.controller;

import com.homerentsolution.msseguros.assembler.SeguroModelAssembler;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.service.SeguroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Controlador versión 2 con soporte HATEOAS
// Pruebas:
// GET http://localhost:8089/api/v2/seguros
// GET http://localhost:8089/api/v2/seguros/1

@RestController
@RequestMapping("/api/v2/seguros")
@Tag(name = "Seguros V2 (HATEOAS)", description = "Consulta de seguros con enlaces de navegación")
public class SeguroControllerV2 {

    @Autowired
    private SeguroService service;

    @Autowired
    private SeguroModelAssembler assembler;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener seguro HATEOAS", description = "Retorna el seguro con enlaces de navegación relacionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seguro encontrado", content = @Content(schema = @Schema(implementation = SeguroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    })
    public EntityModel<SeguroResponseDTO> buscar(
            @Parameter(description = "Identificador del seguro", example = "1", required = true) @PathVariable Long id) {

        SeguroResponseDTO seguro =
                service.buscarPorId(id);

        return assembler.toModel(seguro);
    }

    @GetMapping
    @Operation(summary = "Listar seguros HATEOAS", description = "Retorna todos los seguros con enlaces de navegación.")
    @ApiResponse(responseCode = "200", description = "Colección HATEOAS")
    public CollectionModel<EntityModel<SeguroResponseDTO>> listar() {

        List<EntityModel<SeguroResponseDTO>> seguros =
                service.listar()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(seguros);
    }
}
