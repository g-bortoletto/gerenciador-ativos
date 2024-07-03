package dev.processo_seletivo.gerenciador_ativos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.dto.ValorMercadoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class AtivoFinanceiroControllerIntegrationTest {

    private static final String API_URI = "/api/v0/ativos-financeiros";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());
    }

    @Test
    public void testIncluirAtivoFinanceiro() throws Exception {
        AtivoFinanceiroDto ativoFinanceiroDto = new AtivoFinanceiroDto();
        ativoFinanceiroDto.setNome("Teste Ativo");
        ativoFinanceiroDto.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RV);
        ativoFinanceiroDto.setDataEmissao(LocalDateTime.now().minusDays(1));
        ativoFinanceiroDto.setDataVencimento(LocalDateTime.now().plusDays(1));

        mockMvc.perform(post(API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ativoFinanceiroDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value("Teste Ativo"));
    }

    @Test
    public void testConsultarAtivosFinanceiros() throws Exception {
        mockMvc.perform(get(API_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(JSONArray.class)));
    }

    @Test
    public void testConsultarAtivoFinanceiro() throws Exception {
        mockMvc.perform(get(API_URI + "/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void testEditarAtivoFinanceiro() throws Exception {
        AtivoFinanceiroDto ativoFinanceiroDto = new AtivoFinanceiroDto();
        ativoFinanceiroDto.setNome("Teste Ativo Editado");
        ativoFinanceiroDto.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RV);
        ativoFinanceiroDto.setDataEmissao(LocalDateTime.now().minusDays(2));
        ativoFinanceiroDto.setDataVencimento(LocalDateTime.now().plusDays(2));

        mockMvc.perform(put(API_URI + "/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ativoFinanceiroDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Teste Ativo Editado"));
    }

    @Test
    public void testRemoverAtivoFinanceiro() throws Exception {
        mockMvc.perform(delete(API_URI + "/3"))
            .andExpect(status().isOk());
    }

    @Test
    public void testIncluirValorMercado() throws Exception {
        ValorMercadoDto valorMercadoDto = new ValorMercadoDto();
        valorMercadoDto.setData(LocalDateTime.now());
        valorMercadoDto.setValor(BigDecimal.valueOf(1000.00));

        mockMvc.perform(post(API_URI + "/1/valores-mercado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valorMercadoDto)))
            .andExpect(status().isOk());
    }

    @Test
    public void testConsultarValoresMercado() throws Exception {
        mockMvc.perform(get(API_URI + "/1/valores-mercado"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(JSONArray.class)));
    }

    @Test
    public void testRemoverValorMercado() throws Exception {
        Long valorMercadoId = 7L;
        mockMvc.perform(delete(API_URI + "/4/valores-mercado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(valorMercadoId)))
            .andExpect(status().isOk());
    }

}
