package com.homerentsolution.msseguros.service;

import com.homerentsolution.msseguros.client.PagoClient;
import com.homerentsolution.msseguros.client.ReservaClient;
import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.model.Seguro;
import com.homerentsolution.msseguros.repository.SeguroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguroService {

    private static final Logger log =
            LoggerFactory.getLogger(SeguroService.class);

    @Autowired
    private SeguroRepository repository;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private PagoClient pagoClient;

    // LISTAR
    public List<SeguroResponseDTO> listar() {

        log.info("Consultando todos los seguros");

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // GUARDAR
    public SeguroResponseDTO guardar(
            SeguroRequestDTO dto) {

        log.info(
                "Creando seguro tipo {} para reserva {}",
                dto.getTipo(),
                dto.getIdReserva()
        );

        Seguro seguro = new Seguro();

        seguro.setTipo(dto.getTipo());
        seguro.setCobertura(dto.getCobertura());
        seguro.setIdReserva(dto.getIdReserva());

        if (seguro.getCobertura() <= 0) {

            log.warn(
                    "Cobertura inválida: {}",
                    seguro.getCobertura()
            );

            throw new RuntimeException(
                    "La cobertura debe ser mayor a 0"
            );
        }

        if (!seguro.getTipo().equalsIgnoreCase("basico")
                && !seguro.getTipo().equalsIgnoreCase("premium")) {

            log.warn(
                    "Tipo de seguro inválido: {}",
                    seguro.getTipo()
            );

            throw new RuntimeException(
                    "Tipo de seguro inválido"
            );
        }

        Object reserva =
                reservaClient.buscarReserva(
                        dto.getIdReserva()
                );

        if (reserva == null) {

            log.warn(
                    "La reserva {} no existe",
                    dto.getIdReserva()
            );

            throw new RuntimeException(
                    "La reserva no existe"
            );
        }

        try {

            pagoClient.buscarPago(1L);

        } catch (Exception e) {

            log.error(
                    "No existe un pago válido asociado"
            );

            throw new RuntimeException(
                    "No existe un pago válido asociado"
            );
        }

        Seguro guardado =
                repository.save(seguro);

        log.info(
                "Seguro guardado correctamente con ID {}",
                guardado.getIdSeguro()
        );

        return convertirDTO(guardado);
    }

    // BUSCAR POR ID
    public SeguroResponseDTO buscarPorId(Long id) {

        log.info(
                "Buscando seguro con ID {}",
                id
        );

        Seguro seguro = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Seguro {} no encontrado",
                            id
                    );

                    return new RuntimeException(
                            "Seguro no encontrado"
                    );
                });

        return convertirDTO(seguro);
    }

    // ACTUALIZAR
    public SeguroResponseDTO actualizar(
            Long id,
            SeguroRequestDTO dto) {

        log.info(
                "Actualizando seguro con ID {}",
                id
        );

        Seguro seguro = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Seguro {} no encontrado",
                            id
                    );

                    return new RuntimeException(
                            "Seguro no encontrado"
                    );
                });

        if (dto.getCobertura() <= 0) {

            throw new RuntimeException(
                    "La cobertura debe ser mayor a 0"
            );
        }

        if (!dto.getTipo().equalsIgnoreCase("basico")
                && !dto.getTipo().equalsIgnoreCase("premium")) {

            throw new RuntimeException(
                    "Tipo de seguro inválido"
            );
        }

        seguro.setTipo(dto.getTipo());
        seguro.setCobertura(dto.getCobertura());
        seguro.setIdReserva(dto.getIdReserva());

        Seguro actualizado =
                repository.save(seguro);

        log.info(
                "Seguro {} actualizado correctamente",
                id
        );

        return convertirDTO(actualizado);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        log.warn(
                "Eliminando seguro con ID {}",
                id
        );

        Seguro seguro = repository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Seguro {} no encontrado",
                            id
                    );

                    return new RuntimeException(
                            "Seguro no encontrado"
                    );
                });

        repository.delete(seguro);

        log.info(
                "Seguro {} eliminado correctamente",
                id
        );
    }

    // BUSCAR POR TIPO
    public List<SeguroResponseDTO> buscarPorTipo(
            String tipo) {

        log.info(
                "Buscando seguros tipo {}",
                tipo
        );

        List<Seguro> seguros =
                repository.findByTipo(tipo);

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros para este tipo"
            );
        }

        return seguros.stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR RESERVA
    public List<SeguroResponseDTO> buscarPorReserva(
            Long idReserva) {

        log.info(
                "Buscando seguros para reserva {}",
                idReserva
        );

        List<Seguro> seguros =
                repository.findByIdReserva(
                        idReserva
                );

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros para esta reserva"
            );
        }

        return seguros.stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR ORDENADOS
    public List<SeguroResponseDTO> buscarPorTipoOrdenado(
            String tipo) {

        log.info(
                "Buscando seguros ordenados para tipo {}",
                tipo
        );

        List<Seguro> seguros =
                repository.findByTipoOrderByCoberturaDesc(
                        tipo
                );

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros ordenados para este tipo"
            );
        }

        return seguros.stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // ENTITY -> DTO
    private SeguroResponseDTO convertirDTO(
            Seguro seguro) {

        SeguroResponseDTO response =
                new SeguroResponseDTO();

        response.setIdSeguro(
                seguro.getIdSeguro()
        );

        response.setTipo(
                seguro.getTipo()
        );

        response.setCobertura(
                seguro.getCobertura()
        );

        response.setIdReserva(
                seguro.getIdReserva()
        );

        return response;
    }
}
