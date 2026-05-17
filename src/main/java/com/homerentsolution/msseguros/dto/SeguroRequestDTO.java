package com.homerentsolution.msseguros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeguroRequestDTO {

    @NotBlank(message = "El tipo de seguro es obligatorio")
    private String tipo;

    @NotNull(message = "La cobertura es obligatoria")
    @Positive(message = "La cobertura debe ser positiva")
    private Double cobertura;

    @NotNull(message = "El id de reserva es obligatorio")
    @Positive(message = "El id de reserva debe ser positivo")
    private Long idReserva;
}
