package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Movimentacao;
import dev.processo_seletivo.gerenciador_ativos.entity.ValorMercado;
import dev.processo_seletivo.gerenciador_ativos.model.Posicao;
import dev.processo_seletivo.gerenciador_ativos.repository.ContaCorrenteRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContaCorrenteServiceUnitTest {

    @Mock
    private MovimentacaoService movimentacaoService;
    @Mock
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;
    @Mock
    private AtivoFinanceiroService ativoFinanceiroService;
    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;

    @InjectMocks
    private ContaCorrenteService contaCorrenteService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testConsultarContaCorrentePorId() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);

        when(contaCorrenteRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(contaCorrente));

        Optional<ContaCorrente> result = contaCorrenteService.consultarContaCorrentePorId(1L);

        assertTrue(result.isPresent());
        verify(contaCorrenteRepository, times(1)).findById(1L);
    }

    @Test
    void testConsultarSaldoContaCorrente() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        LocalDateTime dataPosicao = LocalDateTime.now();
        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(contaCorrente.getId())).thenReturn(Optional.of(contaCorrente));
        when(contaCorrenteServiceHelper.consultarSaldoContaCorrente(contaCorrente, dataPosicao)).thenReturn(new BigDecimal("100.00"));

        BigDecimal saldo = contaCorrenteService.consultarSaldoContaCorrente(contaCorrente.getId(), dataPosicao);

        assertNotNull(saldo);
        assertEquals(new BigDecimal("100.00"), saldo);
    }

    @Test
    void testConsultarPosicoesContaCorrente() {
        var contaCorrenteId = 1L;
        var dataPosicao = LocalDateTime.now();

        var ativoFinanceiro = new AtivoFinanceiro();
        ativoFinanceiro.setId(1L);
        ativoFinanceiro.setNome("TESTE_01");
        ativoFinanceiro.setTipo(AtivoFinanceiro.TipoAtivoFinanceiro.RF);
        ativoFinanceiro.setDataEmissao(LocalDateTime.parse("2024-07-01T00:00:00", DateTimeFormatter.ISO_DATE_TIME));
        ativoFinanceiro.setDataVencimento(LocalDateTime.parse("2024-07-05T00:00:00", DateTimeFormatter.ISO_DATE_TIME));

        ValorMercado valorMercado = new ValorMercado();
        valorMercado.setId(1L);
        valorMercado.setAtivoFinanceiro(ativoFinanceiro);
        valorMercado.setData(ativoFinanceiro.getDataEmissao().plusHours(1));
        valorMercado.setValor(BigDecimal.ONE);

        Movimentacao movimentacao1 = new Movimentacao();
        movimentacao1.setAtivoFinanceiro(ativoFinanceiro);
        movimentacao1.setTipo(Movimentacao.TipoMovimentacao.COMPRA);
        movimentacao1.setQuantidade(BigDecimal.valueOf(10));
        movimentacao1.setValor(BigDecimal.valueOf(10.0));

        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setAtivoFinanceiro(ativoFinanceiro);
        movimentacao2.setTipo(Movimentacao.TipoMovimentacao.VENDA);
        movimentacao2.setQuantidade(BigDecimal.valueOf(5));
        movimentacao2.setValor(BigDecimal.valueOf(5.0));

        var movimentacoes = Arrays.asList(movimentacao1, movimentacao2);

        when(ativoFinanceiroService.consultarValorMercado(any(AtivoFinanceiro.class)))
            .thenReturn(valorMercado);
        when(movimentacaoService.consultarMovimentacoesCompra(any(Long.class), any(Long.class), any(LocalDateTime.class)))
            .thenReturn(movimentacoes);
        when(movimentacaoService.consultarMovimentacoesPorContaAteData(eq(contaCorrenteId), any(LocalDateTime.class)))
            .thenReturn(movimentacoes);

        List<Posicao> posicoes = contaCorrenteService.consultarPosicoesContaCorrente(contaCorrenteId, dataPosicao);

        assertEquals(1, posicoes.size());
        Posicao posicao = posicoes.get(0);
        assertEquals(ativoFinanceiro.getNome(), posicao.getAtivoFinanceiro().getNome());
        assertEquals(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.FLOOR), posicao.getQuantidadeTotal());
    }

}
