package com.homerentsolution.msseguros.controller;

import com.homerentsolution.msseguros.assembler.SeguroModelAssembler;
import com.homerentsolution.msseguros.dto.SeguroResponseDTO;
import com.homerentsolution.msseguros.service.SeguroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeguroControllerV2.class)
@Import(SeguroModelAssembler.class)
class SeguroControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeguroService service;

    @Test
    void buscar_debeRetornarSeguroConEnlacesHateoas() throws Exception {
        SeguroResponseDTO response = seguro(1L);
        when(service.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v2/seguros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSeguro").value(1))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.todos-los-seguros.href").exists());

        verify(service).buscarPorId(1L);
    }

    @Test
    void listar_debeRetornarColeccionConEnlacesHateoas() throws Exception {
        when(service.listar()).thenReturn(List.of(seguro(1L), seguro(2L)));

        mockMvc.perform(get("/api/v2/seguros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.seguroResponseDTOList.length()").value(2))
                .andExpect(jsonPath("$._embedded.seguroResponseDTOList[0]._links.self.href").exists());

        verify(service).listar();
    }

    private SeguroResponseDTO seguro(Long id) {
        SeguroResponseDTO response = new SeguroResponseDTO();
        response.setIdSeguro(id);
        response.setTipo("premium");
        response.setCobertura(500000.0);
        response.setIdReserva(10L);
        return response;
    }
}
