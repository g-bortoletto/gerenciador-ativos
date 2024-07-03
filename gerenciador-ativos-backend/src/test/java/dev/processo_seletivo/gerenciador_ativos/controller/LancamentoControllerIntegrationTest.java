package dev.processo_seletivo.gerenciador_ativos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.Lancamento;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.isA;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class LancamentoControllerIntegrationTest {

    static final String API_URI = "/api/v0/lancamentos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final ObjectMapper objMapper = new ObjectMapper();

    private LancamentoDto lancamentoDto = new LancamentoDto(
        1L,
        Lancamento.TipoLancamento.ENTRADA,
        BigDecimal.valueOf(1000.0),
        "",
        LocalDateTime.now());

    @Test
    public void testIncluirLancamentoEntrada() throws Exception {
        lancamentoDto.setDescricao("testIncluirLancamentoEntrada");
        mockMvc.perform(
            post(API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(lancamentoDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valor").value(lancamentoDto.getValor()))
            .andDo(print());
    }

    @Test
    public void testIncluirLancamentoSaidaSaldoInsuficiente() throws Exception {
        lancamentoDto.setTipo(Lancamento.TipoLancamento.SAIDA);
        lancamentoDto.setValor(BigDecimal.valueOf(100_000_000.00));
        lancamentoDto.setDescricao("testIncluirLancamentoSaidaSaldoInsuficiente");
        mockMvc.perform(
                post(API_URI)
                    .content(objMapper.writeValueAsString(lancamentoDto))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    public void testIncluirLancamentoSaidaSaldoSuficiente() throws Exception {
        lancamentoDto.setDescricao("testIncluirLancamentoSaidaSaldoSuficiente");
        mockMvc.perform(
                post(API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objMapper.writeValueAsString(lancamentoDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valor").value(lancamentoDto.getValor()))
            .andDo(print());
    }

    @Test
    public void testConsultarTodosLancamentos() throws Exception {
        lancamentoDto.setDescricao("testConsultarTodosLancamentos");
        mockMvc.perform(
                get(API_URI)
                    .param("conta-corrente-id", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(JSONArray.class)))
            .andDo(print());
    }

    @Test
    public void consultarLancamentosAteData() throws Exception {
        lancamentoDto.setDescricao("testConsultarTodosLancamentos");
        mockMvc.perform(
                get(API_URI)
                    .param("conta-corrente-id", "1")
                    .param("data-f", "2024-07-03T01:00:00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(JSONArray.class)))
            .andDo(print());
    }

    @Test
    public void consultarLancamentosPorPeriodoValido() throws Exception {
        lancamentoDto.setDescricao("testConsultarTodosLancamentos");
        mockMvc.perform(
                get(API_URI)
                    .param("conta-corrente-id", "1")
                    .param("data-f", "2024-07-03T01:00:00")
                    .param("data-i", "2024-07-02T01:00:00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(JSONArray.class)))
            .andDo(print());
    }

    @Test
    public void consultarLancamentoPorPeriodoInvalido() throws Exception {
        lancamentoDto.setDescricao("testConsultarTodosLancamentos");
        mockMvc.perform(
                get(API_URI)
                    .param("conta-corrente-id", "1")
                    .param("data-f", "2024-07-03T01:00:00")
                    .param("data-i", "2024-07-03T01:00:00"))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

}
