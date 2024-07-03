package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContaCorrenteServiceUnitTest {

    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;
    @Mock
    private ContaCorrenteServiceHelper contaCorrenteServiceHelper;

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
    void testIncluirContaCorrente() {
        ContaCorrente contaCorrente = new ContaCorrente();
        when (contaCorrenteRepository.save(any(ContaCorrente.class))).thenReturn(contaCorrente);

        ContaCorrente resultado = contaCorrenteService.incluirContaCorrente();

        assertNotNull(resultado);
        verify(contaCorrenteRepository, times(1)).save(any(ContaCorrente.class));
    }

    @Test
    void testConsultarContasCorrente() {
        List<ContaCorrente> contas = List.of(new ContaCorrente(), new ContaCorrente());
        when(contaCorrenteRepository.findAll()).thenReturn(contas);

        List<ContaCorrente> result = contaCorrenteService.consultarContasCorrente();

        assertEquals(2, result.size());
        verify(contaCorrenteRepository, times(1)).findAll();
    }

    @Test
    void testConsultarContaCorrentePorId() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        when(contaCorrenteServiceHelper.consultarContaCorrentePorId(anyLong())).thenReturn(Optional.of(contaCorrente));

        Optional<ContaCorrente> result = contaCorrenteService.consultarContaCorrentePorId(1L);

        assertTrue(result.isPresent());
        verify(contaCorrenteServiceHelper, times(1)).consultarContaCorrentePorId(1L);
    }

    @Test
    void testRemoverContaCorrentePorId() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(1L);
        when(contaCorrenteService.consultarContaCorrentePorId(anyLong())).thenReturn(Optional.of(contaCorrente));

        boolean result = contaCorrenteService.removerContaCorrentePorId(1L);

        assertTrue(result);
        verify(contaCorrenteRepository, times(1)).delete(contaCorrente);
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

}
