package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.repository.LancamentoRepository;
import dev.processo_seletivo.gerenciador_ativos.service.helper.ContaCorrenteServiceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LancamentoServiceUnitTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    @Mock
    private ContaCorrenteService contaCorrenteService;

    @Mock
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

    @InjectMocks
    private LancamentoService lancamentoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIncluirLancamentoComSaldoSuficiente() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        Lancamento lancamento = new Lancamento();
        LancamentoDto lancamentoDto = new LancamentoDto(
            1L,
            Lancamento.TipoLancamento.ENTRADA,
            new BigDecimal("100.00"),
            "Teste",
            LocalDateTime.now()
        );

        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(anyLong())).thenReturn(Optional.of(contaCorrente));
        when(lancamentoRepository.save(any(Lancamento.class))).thenReturn(lancamento);
        when(contaCorrenteService.consultarSaldoContaCorrente(anyLong(), any(LocalDateTime.class)))
            .thenReturn(new BigDecimal("100.00"));

        Lancamento result = lancamentoService.incluirLancamento(lancamentoDto);

        assertNotNull(result);
        verify(lancamentoRepository, times(1)).save(any(Lancamento.class));
    }

    @Test
    public void testIncluirLancamentoComSaldoInsuficiente() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        Lancamento lancamento = new Lancamento();
        lancamento.setId(1L);
        LancamentoDto lancamentoDto = new LancamentoDto(
            1L,
            Lancamento.TipoLancamento.SAIDA,
            new BigDecimal("100.00"),
            "Teste",
            LocalDateTime.now()
        );

        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(anyLong())).thenReturn(Optional.of(contaCorrente));
        when(lancamentoRepository.save(any(Lancamento.class))).thenReturn(lancamento);
        when(contaCorrenteServiceHelper.consultarSaldoContaCorrente(any(ContaCorrente.class), any(LocalDateTime.class)))
            .thenReturn(new BigDecimal("50.00"));

        assertThrows(RuntimeException.class, () -> {
            lancamentoService.incluirLancamento(lancamentoDto);
        });
        verify(lancamentoRepository, times(0)).save(any(Lancamento.class));
    }

    @Test
    public void testConsultarLancamentosPorContaCorrente() {
        ContaCorrente contaCorrente = new ContaCorrente();
        List<Lancamento> lancamentos = List.of(new Lancamento(), new Lancamento());

        when(lancamentoRepository.findByContaCorrente(any(ContaCorrente.class))).thenReturn(lancamentos);

        List<Lancamento> result = lancamentoService.consultarLancamentosPorContaCorrente(contaCorrente);

        assertEquals(2, result.size());
        verify(lancamentoRepository, times(1)).findByContaCorrente(contaCorrente);
    }

    @Test
    public void testConsultarLancamentosPorContaCorrenteAteData() {
        ContaCorrente contaCorrente = new ContaCorrente();
        LocalDateTime data = LocalDateTime.now();
        Lancamento lancamento = new Lancamento();
        lancamento.setData(data.minusDays(1));
        List<Lancamento> lancamentos = List.of(lancamento);

        when(lancamentoRepository.findByContaCorrente(any(ContaCorrente.class))).thenReturn(lancamentos);

        List<Lancamento> result = lancamentoService.consultarLancamentosPorContaCorrenteAteData(contaCorrente, data);

        assertEquals(1, result.size());
        verify(lancamentoRepository, times(1)).findByContaCorrente(contaCorrente);
    }

}
