package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.model.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.model.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.dto.ValorMercadoDto;
import dev.processo_seletivo.gerenciador_ativos.repository.ValorMercadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ValorMercadoServiceUnitTest {

    @Mock
    private ValorMercadoRepository valorMercadoRepository;

    @InjectMocks
    private ValorMercadoService valorMercadoService;

    private AtivoFinanceiro ativoFinanceiro;
    private ValorMercadoDto valorMercadoDto;
    private ValorMercado valorMercado;

    @BeforeEach
    public void setUp() {
        ativoFinanceiro = new AtivoFinanceiro();
        ativoFinanceiro.setId(1L);
        ativoFinanceiro.setNome("Ativo Teste");
        ativoFinanceiro.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RV);
        ativoFinanceiro.setDataEmissao(LocalDateTime.of(2020, 1, 1, 0, 0));
        ativoFinanceiro.setDataVencimento(LocalDateTime.of(2030, 1, 1, 0, 0));

        valorMercadoDto = new ValorMercadoDto();
        valorMercadoDto.setData(LocalDateTime.of(2021, 1, 1, 0, 0));
        valorMercadoDto.setValor(BigDecimal.valueOf(100.0));

        valorMercado = new ValorMercado();
        valorMercado.setId(1L);
        valorMercado.setAtivoFinanceiro(ativoFinanceiro);
        valorMercado.setData(valorMercadoDto.getData());
        valorMercado.setValor(valorMercadoDto.getValor());
    }

    @Test
    public void testIncluirValorMercadoEmAtivo() {
        when(valorMercadoRepository.save(any(ValorMercado.class))).thenReturn(valorMercado);

        ValorMercado result = valorMercadoService.incluirValorMercadoEmAtivo(ativoFinanceiro, valorMercadoDto);

        assertNotNull(result);
        assertEquals(valorMercado.getId(), result.getId());
        assertEquals(valorMercado.getAtivoFinanceiro(), result.getAtivoFinanceiro());
        assertEquals(valorMercado.getData(), result.getData());
        assertEquals(valorMercado.getValor(), result.getValor());
    }

    @Test
    public void testConsultarValoresMercado() {
        when(valorMercadoRepository.findAll()).thenReturn(Arrays.asList(valorMercado));

        List<ValorMercado> result = valorMercadoService.consultarValoresMercado();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(valorMercado.getId(), result.get(0).getId());
    }

    @Test
    public void testConsultarValorMercadoPorId() {
        when(valorMercadoRepository.findById(1L)).thenReturn(Optional.of(valorMercado));

        Optional<ValorMercado> result = valorMercadoService.consultarValorMercadoPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(valorMercado.getId(), result.get().getId());
    }

    @Test
    public void testConsultarValoresMercadoDeAtivo() {
        when(valorMercadoRepository.findByAtivoFinanceiro(ativoFinanceiro)).thenReturn(Arrays.asList(valorMercado));

        List<ValorMercado> result = valorMercadoService.consultarValoresMercadoDeAtivo(ativoFinanceiro);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(valorMercado.getId(), result.get(0).getId());
    }

    @Test
    public void testConsultarValoresMercadoDeAtivoDesde() {
        when(valorMercadoRepository.findByAtivoFinanceiro(ativoFinanceiro)).thenReturn(List.of(valorMercado));

        List<ValorMercado> result = valorMercadoService.consultarValoresMercadoDeAtivoDesde(ativoFinanceiro, LocalDateTime.of(2020, 1, 1, 0, 0));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testConsultarValoresMercadoDeAtivoNoPeriodo() {
        when(valorMercadoRepository.findByAtivoFinanceiro(ativoFinanceiro)).thenReturn(Arrays.asList(valorMercado));

        List<ValorMercado> result = valorMercadoService.consultarValoresMercadoDeAtivoNoPeriodo(ativoFinanceiro, LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(valorMercado.getId(), result.get(0).getId());
    }

    @Test
    public void testValidarDataPosicaoInvalida() {
        LocalDateTime dataInvalida = LocalDateTime.of(2019, 12, 31, 0, 0);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            valorMercadoService.incluirValorMercadoEmAtivo(ativoFinanceiro, new ValorMercadoDto(BigDecimal.valueOf(100.0), dataInvalida));
        });

        String expectedMessage = "A data do valor de mercado deve estar entre a data de emissão e a data de vencimento do ativo financeiro.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}