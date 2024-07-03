package dev.processo_seletivo.gerenciador_ativos.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class ContaCorrenteControllerIntegrationTest {

    static final String API_URI = "/api/v0/contas-corrente";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void consultarSaldoContaCorrenteInexistente() throws Exception {
        mockMvc.perform(
                get(API_URI + "/2/saldo"))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void consultarSaldoContaCorrenteExistente() throws Exception {
        mockMvc.perform(get(API_URI + "/1/saldo/" + LocalDateTime.now()))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void consultarPosicoesContaCorrente() throws Exception {
        mockMvc.perform(get(API_URI + "/1/posicoes/" + LocalDateTime.now()))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
