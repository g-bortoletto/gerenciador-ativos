package dev.processo_seletivo.gerenciador_ativos.service;

import dev.processo_seletivo.gerenciador_ativos.dto.AtivoFinanceiroDto;
import dev.processo_seletivo.gerenciador_ativos.entity.AtivoFinanceiro;
import dev.processo_seletivo.gerenciador_ativos.repository.AtivoFinanceiroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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

    @InjectMocks
    private AtivoFinanceiroService ativoFinanceiroService;

    private AtivoFinanceiro ativoFinanceiro;
    private AtivoFinanceiroDto ativoFinanceiroDto;

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

}