package com.homerentsolution.msseguros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seguros")

@Getter
@Setter
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    // Tipo de seguro (basico o premium)
    @NotBlank(message = "El tipo de seguro es obligatorio")
    @Column(nullable = false)
    private String tipo;

    // Cobertura del seguro
    @NotNull(message = "La cobertura es obligatoria")
    @Positive(message = "La cobertura debe ser positiva")
    @Column(nullable = false)
    private Double cobertura;

    // ID de la reserva asociada
    @NotNull(message = "El id de reserva es obligatorio")
    @Positive(message = "El id de reserva debe ser positivo")
    @Column(nullable = false)
    private Long idReserva;
}