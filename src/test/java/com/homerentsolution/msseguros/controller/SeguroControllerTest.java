package com.homerentsolution.msseguros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homerentsolution.msseguros.dto.SeguroRequestDTO;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.service.SeguroService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeguroController.class)
class SeguroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SeguroService service;

    @Test
    void listar_debeRetornarStatus200() throws Exception {

        SeguroResponseDTO response = new SeguroResponseDTO();
        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(1L);

        when(service.listar())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/seguros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSeguro").value(1))
                .andExpect(jsonPath("$[0].tipo").value("premium"));

        verify(service, times(1)).listar();
    }

    @Test
    void buscar_debeRetornarStatus200() throws Exception {

        SeguroResponseDTO response = new SeguroResponseDTO();
        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(1L);

        when(service.buscarPorId(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/seguros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSeguro").value(1))
                .andExpect(jsonPath("$.tipo").value("premium"));

        verify(service).buscarPorId(1L);
    }

    @Test
    void guardar_debeRetornarStatus201() throws Exception {

        SeguroRequestDTO request =
                new SeguroRequestDTO();

        request.setTipo("premium");
        request.setCobertura(500000.0);
        request.setIdReserva(1L);

        SeguroResponseDTO response =
                new SeguroResponseDTO();

        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(1L);

        when(service.guardar(any(SeguroRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/seguros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSeguro").value(1));

        verify(service).guardar(any(SeguroRequestDTO.class));
    }

    @Test
    void guardar_conDatosInvalidos_debeRetornar400()
            throws Exception {

        SeguroRequestDTO request =
                new SeguroRequestDTO();

        request.setTipo("");
        request.setCobertura(500000.0);
        request.setIdReserva(1L);

        mockMvc.perform(post("/api/v1/seguros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(service, never())
                .guardar(any());
    }

    @Test
    void actualizar_debeRetornarStatus200() throws Exception {

        SeguroRequestDTO request =
                new SeguroRequestDTO();

        request.setTipo("premium");
        request.setCobertura(800000.0);
        request.setIdReserva(1L);

        SeguroResponseDTO response =
                new SeguroResponseDTO();

        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(800000.0);
        response.setIdReserva(1L);

        when(service.actualizar(
                eq(1L),
                any(SeguroRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/seguros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSeguro").value(1));

        verify(service)
                .actualizar(eq(1L),
                        any(SeguroRequestDTO.class));
    }

    @Test
    void eliminar_debeRetornar204() throws Exception {

        doNothing().when(service)
                .eliminar(1L);

        mockMvc.perform(delete("/api/v1/seguros/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminar(1L);
    }

    @Test
    void buscarPorTipo_debeRetornar200() throws Exception {

        SeguroResponseDTO response =
                new SeguroResponseDTO();

        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(1L);

        when(service.buscarPorTipo("premium"))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/v1/seguros/tipo/premium"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo")
                        .value("premium"));

        verify(service)
                .buscarPorTipo("premium");
    }

    @Test
    void buscarPorReserva_debeRetornar200()
            throws Exception {

        SeguroResponseDTO response =
                new SeguroResponseDTO();

        response.setIdSeguro(1L);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(1L);

        when(service.buscarPorReserva(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/v1/seguros/reserva/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReserva")
                        .value(1));

        verify(service)
                .buscarPorReserva(1L);
    }
}
