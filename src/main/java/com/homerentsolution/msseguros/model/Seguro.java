package com.homerentsolution.msseguros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "seguros")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    @NotBlank(message = "El tipo de seguro es obligatorio")
    @Column(nullable = false)
    private String tipo;

    @NotNull(message = "La cobertura es obligatoria")
    @Positive(message = "La cobertura debe ser positiva")
    @Column(nullable = false)
    private Double cobertura;

    @NotNull(message = "El id de reserva es obligatorio")
    @Positive(message = "El id de reserva debe ser positivo")
    @Column(nullable = false)
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