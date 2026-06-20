package com.homerentsolution.msseguros.service;

import com.homerentsolution.msseguros.client.PagoClient;
import com.homerentsolution.msseguros.client.ReservaClient;
import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.model.Seguro;
import com.homerentsolution.msseguros.repository.SeguroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeguroServiceTest {

    @Mock
    private SeguroRepository repository;

    @Mock
    private ReservaClient reservaClient;

    @Mock
    private PagoClient pagoClient;

    @InjectMocks
    private SeguroService service;

    @Test
    void listar_debeRetornarListaSeguros() {

        // GIVEN
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(1L);
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        seguro.setIdReserva(1L);

        when(repository.findAll())
                .thenReturn(List.of(seguro));

        // WHEN
        List<SeguroResponseDTO> resultado =
                service.listar();

        // THEN
        assertEquals(1, resultado.size());
        assertEquals("premium",
                resultado.get(0).getTipo());

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarSeguro() {

        // GIVEN
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(1L);
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        seguro.setIdReserva(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(seguro));

        // WHEN
        SeguroResponseDTO resultado =
                service.buscarPorId(1L);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L,
                resultado.getIdSeguro());

        verify(repository, times(1))
                .findById(1L);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {

        // GIVEN
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.buscarPorId(99L)
                );

        assertEquals(
                "Seguro no encontrado",
                exception.getMessage()
        );
    }

    @Test
    void guardar_cuandoDatosValidos_debeGuardarSeguro() {

        // GIVEN
        SeguroRequestDTO dto =
                new SeguroRequestDTO();

        dto.setTipo("premium");
        dto.setCobertura(500000.0);
        dto.setIdReserva(1L);

        when(reservaClient.buscarReserva(1L))
                .thenReturn(new Object());

        when(pagoClient.buscarPago(1L))
                .thenReturn(new Object());

        Seguro guardado = new Seguro();

        guardado.setIdSeguro(1L);
        guardado.setTipo("premium");
        guardado.setCobertura(500000.0);
        guardado.setIdReserva(1L);

        when(repository.save(any(Seguro.class)))
                .thenReturn(guardado);

        // WHEN
        SeguroResponseDTO resultado =
                service.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L,
                resultado.getIdSeguro());

        verify(repository, times(1))
                .save(any(Seguro.class));
    }

    private SeguroRequestDTO seguroValido() {
        SeguroRequestDTO dto = new SeguroRequestDTO();
        dto.setTipo("premium");
        dto.setCobertura(500000.0);
        dto.setIdReserva(1L);
        return dto;
    }

    @Test
    void guardar_rechazaCoberturaInvalida() {
        SeguroRequestDTO dto = seguroValido();
        dto.setCobertura(0.0);
        assertEquals("La cobertura debe ser mayor a 0",
                assertThrows(RuntimeException.class, () -> service.guardar(dto)).getMessage());
    }

    @Test
    void guardar_rechazaTipoInvalido() {
        SeguroRequestDTO dto = seguroValido();
        dto.setTipo("otro");
        assertEquals("Tipo de seguro inválido",
                assertThrows(RuntimeException.class, () -> service.guardar(dto)).getMessage());
    }

    @Test
    void guardar_rechazaReservaInexistente() {
        SeguroRequestDTO dto = seguroValido();
        when(reservaClient.buscarReserva(1L)).thenReturn(null);
        assertEquals("La reserva no existe",
                assertThrows(RuntimeException.class, () -> service.guardar(dto)).getMessage());
    }

    @Test
    void guardar_rechazaPagoInexistente() {
        SeguroRequestDTO dto = seguroValido();
        when(reservaClient.buscarReserva(1L)).thenReturn(new Object());
        when(pagoClient.buscarPago(1L)).thenThrow(new RuntimeException("sin pago"));
        assertEquals("No existe un pago válido asociado",
                assertThrows(RuntimeException.class, () -> service.guardar(dto)).getMessage());
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarSeguro() {

        // GIVEN
        Seguro existente = new Seguro();

        existente.setIdSeguro(1L);
        existente.setTipo("basico");
        existente.setCobertura(100000.0);
        existente.setIdReserva(1L);

        SeguroRequestDTO dto =
                new SeguroRequestDTO();

        dto.setTipo("premium");
        dto.setCobertura(500000.0);
        dto.setIdReserva(1L);

        Seguro actualizado =
                new Seguro();

        actualizado.setIdSeguro(1L);
        actualizado.setTipo("premium");
        actualizado.setCobertura(500000.0);
        actualizado.setIdReserva(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(any(Seguro.class)))
                .thenReturn(actualizado);

        // WHEN
        SeguroResponseDTO resultado =
                service.actualizar(1L, dto);

        // THEN
        assertEquals("premium",
                resultado.getTipo());

        verify(repository, times(1))
                .save(any(Seguro.class));
    }

    @Test
    void actualizar_rechazaSeguroInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertEquals("Seguro no encontrado",
                assertThrows(RuntimeException.class, () -> service.actualizar(99L, seguroValido())).getMessage());
    }

    @Test
    void actualizar_rechazaCoberturaInvalida() {
        Seguro existente = new Seguro();
        SeguroRequestDTO dto = seguroValido();
        dto.setCobertura(0.0);
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        assertEquals("La cobertura debe ser mayor a 0",
                assertThrows(RuntimeException.class, () -> service.actualizar(1L, dto)).getMessage());
    }

    @Test
    void actualizar_rechazaTipoInvalido() {
        Seguro existente = new Seguro();
        SeguroRequestDTO dto = seguroValido();
        dto.setTipo("otro");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        assertEquals("Tipo de seguro inválido",
                assertThrows(RuntimeException.class, () -> service.actualizar(1L, dto)).getMessage());
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarSeguro() {

        // GIVEN
        Seguro seguro = new Seguro();

        seguro.setIdSeguro(1L);
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        seguro.setIdReserva(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(seguro));

        // WHEN
        service.eliminar(1L);

        // THEN
        verify(repository, times(1))
                .delete(seguro);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertEquals("Seguro no encontrado",
                assertThrows(RuntimeException.class, () -> service.eliminar(99L)).getMessage());
    }

    @Test
    void buscarPorTipo_debeRetornarLista() {

        // GIVEN
        Seguro seguro = new Seguro();

        seguro.setIdSeguro(1L);
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        seguro.setIdReserva(1L);

        when(repository.findByTipo("premium"))
                .thenReturn(List.of(seguro));

        // WHEN
        List<SeguroResponseDTO> resultado =
                service.buscarPorTipo("premium");

        // THEN
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorReserva_debeRetornarLista() {

        // GIVEN
        Seguro seguro = new Seguro();

        seguro.setIdSeguro(1L);
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        seguro.setIdReserva(1L);

        when(repository.findByIdReserva(1L))
                .thenReturn(List.of(seguro));

        // WHEN
        List<SeguroResponseDTO> resultado =
                service.buscarPorReserva(1L);

        // THEN
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorTipo_vacio_debeLanzarExcepcion() {
        when(repository.findByTipo("premium")).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> service.buscarPorTipo("premium"));
    }

    @Test
    void buscarPorReserva_vacio_debeLanzarExcepcion() {
        when(repository.findByIdReserva(1L)).thenReturn(List.of());
        assertThrows(RuntimeException.class, () -> service.buscarPorReserva(1L));
    }

    @Test
    void buscarPorTipoOrdenado_debeRetornarLista() {
        Seguro seguro = new Seguro();
        seguro.setTipo("premium");
        seguro.setCobertura(500000.0);
        when(repository.findByTipoOrderByCoberturaDesc("premium")).thenReturn(List.of(seguro));
        assertEquals(1, service.buscarPorTipoOrdenado("premium").size());
    }
}
