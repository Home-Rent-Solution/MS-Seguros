package com.homerentsolution.msseguros.dto;

import io.swagger.v3.oas.annotations.media.Schema;
// Define la documentación del DTO de respuesta en Swagger
@Schema(
        name = "SeguroResponseDTO",
        description = "Respuesta con información del seguro"
)
public class SeguroResponseDTO {

    @Schema(
            description = "Identificador único del seguro",
            example = "1"
    )
    private Long idSeguro;


    @Schema(
            description = "Tipo de seguro contratado",
            example = "Premium"
    )
    private String tipo;


    @Schema(
            description = "Monto de cobertura del seguro",
            example = "500000"
    )
    private Double cobertura;


    @Schema(
            description = "Identificador de la reserva asociada",
            example = "1"
    )
    private Long idReserva;

    public SeguroResponseDTO() {
    }

    public Long getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Long idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCobertura() {
        return cobertura;
    }

    public void setCobertura(Double cobertura) {
        this.cobertura = cobertura;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
}