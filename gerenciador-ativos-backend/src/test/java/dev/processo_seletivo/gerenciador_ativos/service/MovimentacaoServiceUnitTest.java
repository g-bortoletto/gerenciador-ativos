package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.dto.MovimentacaoDto;
import dev.processo_seletivo.gerenciador_ativos.model.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.model.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.repository.MovimentacaoRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovimentacaoServiceUnitTest {

    @Mock
    private AtivoFinanceiroService ativoFinanceiroService;

    @Mock
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private LancamentoService lancamentoService;

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    private MovimentacaoDto movimentacaoDto;
    private AtivoFinanceiro ativoFinanceiro;
    private ContaCorrente contaCorrente;

    @BeforeEach
    void setUp() {
        movimentacaoDto = new MovimentacaoDto();
        movimentacaoDto.setContaCorrenteId(1L);
        movimentacaoDto.setTipo(Movimentacao.TipoMovimentacao.COMPRA);
        movimentacaoDto.setAtivoFinanceiroId(1L);
        movimentacaoDto.setQuantidade(new BigDecimal("10"));
        movimentacaoDto.setValor(new BigDecimal("100"));
        movimentacaoDto.setData(LocalDateTime.now());

        ativoFinanceiro = new AtivoFinanceiro();
        ativoFinanceiro.setDataEmissao(LocalDateTime.now().minusDays(1));
        ativoFinanceiro.setDataVencimento(LocalDateTime.now().plusDays(1));

        contaCorrente = new ContaCorrente();
    }

    @Test
    void testIncluirMovimentacaoSuccess() {
        when(ativoFinanceiroService.consultarAtivoFinanceiroPorId(movimentacaoDto.getAtivoFinanceiroId()))
            .thenReturn(ativoFinanceiro);

        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(movimentacaoDto.getContaCorrenteId()))
            .thenReturn(Optional.of(contaCorrente));

        when(contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, movimentacaoDto.getData()))
            .thenReturn(new BigDecimal("1000"));

        when(lancamentoService.incluirLancamento(
            eq(movimentacaoDto.getContaCorrenteId()), any(LancamentoDto.class)))
            .thenReturn(new Lancamento());

        when(movimentacaoRepository.save(any(Movimentacao.class)))
            .thenReturn(new Movimentacao());

        Movimentacao result = movimentacaoService.incluirMovimentacao(movimentacaoDto);
        assertNotNull(result);
    }

    @Test
    void testIncluirMovimentacaoInvalidDate() {
        movimentacaoDto.setData(LocalDateTime.now().plusDays(10));

        when(ativoFinanceiroService.consultarAtivoFinanceiroPorId(movimentacaoDto.getAtivoFinanceiroId()))
            .thenReturn(ativoFinanceiro);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            movimentacaoService.incluirMovimentacao(movimentacaoDto));

        assertEquals("Data de movimentação inválida", exception.getMessage());
    }

    @Test
    void testIncluirMovimentacaoSaldoInsuficiente() {
        movimentacaoDto.setTipo(Movimentacao.TipoMovimentacao.VENDA);

        when(ativoFinanceiroService.consultarAtivoFinanceiroPorId(movimentacaoDto.getAtivoFinanceiroId()))
            .thenReturn(ativoFinanceiro);

        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(movimentacaoDto.getContaCorrenteId()))
            .thenReturn(Optional.of(contaCorrente));

        when(contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, movimentacaoDto.getData()))
            .thenReturn(new BigDecimal("500"));

        Exception exception = assertThrows(RuntimeException.class, () ->
            movimentacaoService.incluirMovimentacao(movimentacaoDto));

        assertEquals("Saldo insuficiente para realizar movimentação.", exception.getMessage());
    }

    @Test
    void testConsultarMovimentacoesPorContaSuccess() {
        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(1L))
            .thenReturn(Optional.of(contaCorrente));

        when(movimentacaoRepository.findByContaCorrente(contaCorrente))
            .thenReturn(List.of(new Movimentacao()));

        List<Movimentacao> result = movimentacaoService.consultarMovimentacoesPorConta(1L);
        assertFalse(result.isEmpty());
    }

    @Test
    void testConsultarMovimentacoesPorContaNotFound() {
        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(1L))
            .thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () ->
            movimentacaoService.consultarMovimentacoesPorConta(1L));

        assertEquals("Conta corrente não existe.", exception.getMessage());
    }

}