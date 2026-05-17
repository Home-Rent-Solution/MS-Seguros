package com.homerentsolution.msseguros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seguros")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    private String tipo;

    private Double cobertura;

    private Long idReserva;

    public Seguro() {
    }

    public Seguro(Long idSeguro,
                  String tipo,
                  Double cobertura,
                  Long idReserva) {

        this.idSeguro = idSeguro;
        this.tipo = tipo;
        this.cobertura = cobertura;
        this.idReserva = idReserva;
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