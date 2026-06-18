package com.homerentsolution.msseguros.controller;

import com.homerentsolution.msseguros.assembler.SeguroModelAssembler;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.service.SeguroService;

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
public class SeguroControllerV2 {

    @Autowired
    private SeguroService service;

    @Autowired
    private SeguroModelAssembler assembler;

    @GetMapping("/{id}")
    public EntityModel<SeguroResponseDTO> buscar(
            @PathVariable Long id) {

        SeguroResponseDTO seguro =
                service.buscarPorId(id);

        return assembler.toModel(seguro);
    }

    @GetMapping
    public CollectionModel<EntityModel<SeguroResponseDTO>> listar() {

        List<EntityModel<SeguroResponseDTO>> seguros =
                service.listar()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(seguros);
    }
}
