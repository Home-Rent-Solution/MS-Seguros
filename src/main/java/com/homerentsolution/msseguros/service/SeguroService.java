package com.homerentsolution.msseguros.service;

import com.homerentsolution.msseguros.client.PagoClient;
import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.model.Seguro;
import com.homerentsolution.msseguros.repository.SeguroRepository;
import com.homerentsolution.msseguros.client.ReservaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository repository;

    //se agrega Feing para comunicación entre servicios
    @Autowired
    private ReservaClient reservaClient;

    // Feign pagos
    @Autowired
    private PagoClient pagoClient;

    //listar todos los seguros
    public List<Seguro> listar() {

        return repository.findAll();
    }

    //guardar un nuevo seguro dto
    public SeguroResponseDTO guardar(
            SeguroRequestDTO dto) {

        // Convertir DTO a Entity
        Seguro seguro = new Seguro();

        seguro.setTipo(dto.getTipo());
        seguro.setCobertura(dto.getCobertura());
        seguro.setIdReserva(dto.getIdReserva());

        // Regla de negocio
        if (seguro.getCobertura() <= 0) {

            throw new RuntimeException(
                    "La cobertura debe ser mayor a 0"
            );
        }

        // Regla de negocio
        if (!seguro.getTipo().equalsIgnoreCase("basico") &&
                !seguro.getTipo().equalsIgnoreCase("premium")) {

            throw new RuntimeException(
                    "Tipo de seguro inválido"
            );
        }
        // Validar existencia de reserva en MS-Reservas
        Object reserva =
                reservaClient.buscarReserva(
                        dto.getIdReserva()
                );

        if (reserva == null) {

            throw new RuntimeException(
                    "La reserva no existe"
            );
        }

        // validar pago antes de emitir seguro
        try {

            pagoClient.buscarPago(1L);

        } catch (Exception e) {

            throw new RuntimeException(
                    "No existe un pago válido asociado"
            );
        }

        Seguro guardado = repository.save(seguro);

        return convertirDTO(guardado);
    }

    // buscar por Id
    public Seguro buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Seguro no encontrado"
                        ));
    }

    // Actualizar
    public SeguroResponseDTO actualizar(Long id,
                                        SeguroRequestDTO dto) {

        Seguro seguro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Seguro no encontrado"
                        ));

        // Regla de negocio
        // La cobertura debe ser mayor a 0
        if (dto.getCobertura() <= 0) {

            throw new RuntimeException(
                    "La cobertura debe ser mayor a 0"
            );
        }

        // Validar tipo de seguro
        if (!dto.getTipo().equalsIgnoreCase("basico") &&
                !dto.getTipo().equalsIgnoreCase("premium")) {

            throw new RuntimeException(
                    "Tipo de seguro inválido"
            );
        }

        seguro.setTipo(
                dto.getTipo()
        );

        seguro.setCobertura(
                dto.getCobertura()
        );

        seguro.setIdReserva(
                dto.getIdReserva()
        );

        Seguro actualizado = repository.save(seguro);

        return convertirDTO(actualizado);
    }

    private SeguroResponseDTO convertirDTO(Seguro seguro) {

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

    // Eliminar
    public void eliminar(Long id) {

        Seguro seguro = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Seguro no encontrado"
                        ));

        repository.delete(seguro);
    }

    // Buscar seguros por tipo
    public List<Seguro> buscarPorTipo(String tipo) {

        List<Seguro> seguros =
                repository.findByTipo(tipo);

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros para este tipo"
            );
        }

        return seguros;
    }

    // Buscar seguros por reserva
    public List<Seguro> buscarPorReserva(Long idReserva) {

        List<Seguro> seguros =
                repository.findByIdReserva(idReserva);

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros para esta reserva"
            );
        }

        return seguros;
    }

    // Buscar seguros ordenados por cobertura
    public List<Seguro> buscarPorTipoOrdenado(
            String tipo) {

        List<Seguro> seguros =
                repository
                        .findByTipoOrderByCoberturaDesc(
                                tipo
                        );

        if (seguros.isEmpty()) {

            throw new RuntimeException(
                    "No existen seguros ordenados para este tipo"
            );
        }

        return seguros;
    }
}
