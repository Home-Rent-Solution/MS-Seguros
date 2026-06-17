package com.homerentsolution.msseguros.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Define la documentación del DTO en Swagger
@Schema(
        name = "SeguroRequestDTO",
        description = "Datos necesarios para registrar un seguro"
)
public class SeguroRequestDTO {

    @Schema(
            description = "Tipo de seguro",
            example = "premium"
    )
    @NotBlank(message = "El tipo de seguro es obligatorio")
    private String tipo;

    @Schema(
            description = "Cobertura del seguro",
            example = "500000"
    )
    @NotNull(message = "La cobertura es obligatoria")
    @Positive(message = "La cobertura debe ser positiva")
    private Double cobertura;

    @Schema(
            description = "ID de la reserva asociada",
            example = "1"
    )
    @NotNull(message = "El id de reserva es obligatorio")
    @Positive(message = "El id de reserva debe ser positivo")
    private Long idReserva;
}
