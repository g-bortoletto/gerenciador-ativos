package dev.processo_seletivo.gerenciador_ativos.service.helper;

import dev.processo_seletivo.gerenciador_ativos.model.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.repository.ContaCorrenteRepository;
import dev.processo_seletivo.gerenciador_ativos.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
public class ContaCorrenteServiceHelper {

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    LancamentoRepository lancamentoRepository;


    public Optional<ContaCorrente> consultarContaCorrentePorId(Long contaCorrenteId) {
        return contaCorrenteRepository.findById(contaCorrenteId);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrente(ContaCorrente contaCorrente) {
        return lancamentoRepository.findByContaCorrente(contaCorrente);
    }

    public List<Lancamento> consultarLancamentosPorContaCorrenteAteData(ContaCorrente contaCorrente, LocalDateTime data) {
        return consultarLancamentosPorContaCorrente(contaCorrente).stream()
            .filter(lancamento -> lancamento.getData().isBefore(data) || lancamento.getData().isEqual(data))
            .toList();
    }

    public BigDecimal consultarSaldoContaCorrente(ContaCorrente contaCorrente, LocalDateTime dataPosicao) {
        List<Lancamento> listaLancamentos = consultarLancamentosPorContaCorrenteAteData(contaCorrente, dataPosicao);
        System.out.println(listaLancamentos);
        if (listaLancamentos.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Map<Lancamento.TipoLancamento, BigDecimal> valorLancamentosPorTipo = listaLancamentos.stream().collect(
            groupingBy(
                Lancamento::getTipo,
            mapping(
                Lancamento::getValor,
            reducing(
                BigDecimal.ZERO, BigDecimal::add))));

        BigDecimal valorLancamentosEntrada = valorLancamentosPorTipo.getOrDefault(Lancamento.TipoLancamento.ENTRADA, BigDecimal.ZERO);
        BigDecimal valorLancamentosSaida = valorLancamentosPorTipo.getOrDefault(Lancamento.TipoLancamento.SAIDA, BigDecimal.ZERO);
        if (valorLancamentosEntrada.compareTo(valorLancamentosSaida) < 0) {
            throw new RuntimeException("Valor do saldo é negativo.");
        }

        return valorLancamentosEntrada.subtract(valorLancamentosSaida);
    }

}
