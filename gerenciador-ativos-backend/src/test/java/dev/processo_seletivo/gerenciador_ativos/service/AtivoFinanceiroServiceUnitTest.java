package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.dto.ValorMercadoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.repository.AtivoFinanceiroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AtivoFinanceiroServiceUnitTest {

    @Mock
    private AtivoFinanceiroRepository ativoFinanceiroRepository;
    @Mock
    private ValorMercadoService valorMercadoService;

    @InjectMocks
    private AtivoFinanceiroService ativoFinanceiroService;


    private AtivoFinanceiro ativoFinanceiro;
    private AtivoFinanceiroDto ativoFinanceiroDto;
    private ValorMercado valorMercado;

    @BeforeEach
    void setUp() {
        ativoFinanceiro = new AtivoFinanceiro();
        ativoFinanceiro.setId(1L);
        ativoFinanceiro.setNome("Ativo Teste");
        ativoFinanceiro.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RV);
        ativoFinanceiro.setDataEmissao(LocalDateTime.now());
        ativoFinanceiro.setDataVencimento(LocalDateTime.now().plusDays(1));

        ativoFinanceiroDto = new AtivoFinanceiroDto();
        ativoFinanceiroDto.setNome("Ativo Teste");
        ativoFinanceiroDto.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RV);
        ativoFinanceiroDto.setDataEmissao(LocalDateTime.now());
        ativoFinanceiroDto.setDataVencimento(LocalDateTime.now().plusDays(1));

        valorMercado = new ValorMercado();
        valorMercado.setId(1L);
        valorMercado.setValor(BigDecimal.ONE);
        valorMercado.setAtivoFinanceiro(ativoFinanceiro);
        valorMercado.setData(LocalDateTime.of(2024, 7, 4, 0, 1));
    }

    @Test
    void testIncluirAtivoFinanceiro() {
        when(ativoFinanceiroRepository.save(any(AtivoFinanceiro.class))).thenReturn(ativoFinanceiro);

        AtivoFinanceiro result = ativoFinanceiroService.incluirAtivoFinanceiro(ativoFinanceiroDto);

        assertNotNull(result);
        assertEquals(ativoFinanceiro.getNome(), result.getNome());
        verify(ativoFinanceiroRepository, times(1)).save(any(AtivoFinanceiro.class));
    }

    @Test
    void testIncluirAtivoFinanceiroDataInvalida() {
        ativoFinanceiroDto.setDataVencimento(LocalDateTime.now().minusDays(1));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ativoFinanceiroService.incluirAtivoFinanceiro(ativoFinanceiroDto);
        });

        assertEquals("A data de vencimento do ativo deve ser posterior à data de emissão.", exception.getMessage());
    }

    @Test
    void testConsultarAtivosFinanceiros() {
        when(ativoFinanceiroRepository.findAll()).thenReturn(List.of(ativoFinanceiro));

        List<AtivoFinanceiro> result = ativoFinanceiroService.consultarAtivosFinanceiros();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ativoFinanceiro.getNome(), result.get(0).getNome());
    }

    @Test
    void testConsultarAtivoFinanceiroPorId() {
        when(ativoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(ativoFinanceiro));

        AtivoFinanceiro result = ativoFinanceiroService.consultarAtivoFinanceiroPorId(1L);

        assertNotNull(result);
        assertEquals(ativoFinanceiro.getNome(), result.getNome());
    }

    @Test
    void testConsultarAtivoFinanceiroPorIdNaoEncontrado() {
        when(ativoFinanceiroRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ativoFinanceiroService.consultarAtivoFinanceiroPorId(1L);
        });

        assertEquals("Ativo financeiro 1 não encontrado.", exception.getMessage());
    }

    @Test
    void testEditarAtivoFinanceiro() {
        when(ativoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(ativoFinanceiro));
        when(ativoFinanceiroRepository.save(any(AtivoFinanceiro.class))).thenReturn(ativoFinanceiro);

        AtivoFinanceiro result = ativoFinanceiroService.editarAtivoFinanceiro(1L, ativoFinanceiroDto);

        assertNotNull(result);
        assertEquals(ativoFinanceiro.getNome(), result.getNome());
        verify(ativoFinanceiroRepository, times(1)).save(any(AtivoFinanceiro.class));
    }

    @Test
    void testEditarAtivoFinanceiroNaoEncontrado() {
        when(ativoFinanceiroRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ativoFinanceiroService.editarAtivoFinanceiro(1L, ativoFinanceiroDto);
        });

        assertNotNull(exception);
    }

    @Test
    void testRemoverAtivoFinanceiro() {
        when(ativoFinanceiroRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(ativoFinanceiro));

        AtivoFinanceiro resultado = ativoFinanceiroService.removerAtivoFinanceiro(ativoFinanceiro.getId());

        assertNotNull(resultado);
    }

    @Test
    void testRemoverAtivoFinanceiroInvalido() {
        when(ativoFinanceiroRepository.findById(any(Long.class)))
            .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> ativoFinanceiroService.removerAtivoFinanceiro(ativoFinanceiro.getId()));
    }

    @Test
    void testIncluirValorMercado() {
        when(ativoFinanceiroRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(ativoFinanceiro));
        when(valorMercadoService.incluirValorMercadoEmAtivo(any(AtivoFinanceiro.class), any(ValorMercadoDto.class)))
            .thenReturn(valorMercado);

        ValorMercado resultado = ativoFinanceiroService.incluirValorMercado(
            ativoFinanceiro.getId(),
            valorMercado.getData(),
            valorMercado.getValor());

        assertNotNull(resultado);
    }

    // public ValorMercado consultarValorMercado(AtivoFinanceiro ativoFinanceiro)
    @Test
    public void testConsultarValorMercado() {
        when(valorMercadoService.consultarValorMercadoMaisRecenteDeAtivo(any(AtivoFinanceiro.class)))
            .thenReturn(valorMercado);

        ValorMercado resultado = ativoFinanceiroService.consultarValorMercado(ativoFinanceiro);

        assertNotNull(resultado);
        assertEquals(valorMercado, resultado);
    }

    @Test
    public void testConsultarValoresMercado() {
        List<ValorMercado> valoresMercado = Collections.singletonList(valorMercado);
        when(ativoFinanceiroRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(ativoFinanceiro));
        when(valorMercadoService.consultarValoresMercadoDeAtivo(any(AtivoFinanceiro.class)))
            .thenReturn(valoresMercado);

        List<ValorMercado> resultado = ativoFinanceiroService.consultarValoresMercado(ativoFinanceiro.getId());

        assertNotNull(resultado);
        assertArrayEquals(valoresMercado.toArray(), resultado.toArray());
    }

}
