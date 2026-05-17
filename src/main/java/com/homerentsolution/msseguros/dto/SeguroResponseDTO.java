package com.homerentsolution.msseguros.dto;

public class SeguroResponseDTO {

    private Long idSeguro;
    private String tipo;
    private Double cobertura;
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