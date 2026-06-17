package com.homerentsolution.msseguros.assembler;

import com.homerentsolution.msseguros.controller.SeguroControllerV2;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
// Agrega enlaces HATEOAS a los recursos Seguro
@Component
public class SeguroModelAssembler
        implements RepresentationModelAssembler<
        SeguroResponseDTO,
        EntityModel<SeguroResponseDTO>> {

    @Override
    public EntityModel<SeguroResponseDTO> toModel(
            SeguroResponseDTO seguro) {

        return EntityModel.of(
                seguro,

                linkTo(
                        methodOn(
                                SeguroControllerV2.class)
                                .buscar(seguro.getIdSeguro())
                ).withSelfRel(),

                linkTo(
                        methodOn(
                                SeguroControllerV2.class)
                                .listar()
                ).withRel("todos-los-seguros")
        );
    }
}
