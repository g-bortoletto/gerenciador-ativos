package dev.processo_seletivo.gerenciador_ativos.controller;

import dev.processo_seletivo.gerenciador_ativos.dto.LancamentoDto;
import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.entity.Lancamento;
import dev.processo_seletivo.gerenciador_ativos.exception.ContaInexistenteException;
import dev.processo_seletivo.gerenciador_ativos.exception.SaldoInsuficienteException;
import dev.processo_seletivo.gerenciador_ativos.model.RespostaErro;
import dev.processo_seletivo.gerenciador_ativos.service.LancamentoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/v0/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity<?> incluirLancamento(@RequestBody @NotNull LancamentoDto lancamentoDto) {
        try {
            return ResponseEntity.ok(lancamentoService.incluirLancamento(lancamentoDto));
        } catch (ContaInexistenteException e) {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            return new ResponseEntity<RespostaErro>(
                new RespostaErro(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage()),
                httpStatus);
        } catch (SaldoInsuficienteException e) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<RespostaErro>(
                new RespostaErro(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage()),
                httpStatus);
        }
    }

    @GetMapping
    public ResponseEntity<List<Lancamento>> consultarLancamentosPorContaCorrentePorData(@RequestParam("conta-corrente-id") Long contaCorrenteId, @RequestParam(value = "data-i", required = false) LocalDateTime primeiraData, @RequestParam(value = "data-f", required = false)LocalDateTime segundaData) {
        ContaCorrente contaCorrente = criarContaCorrenteComId(contaCorrenteId);

        if (primeiraData == null && segundaData == null) {
            return ResponseEntity.ok(lancamentoService.consultarLancamentosPorContaCorrente(contaCorrente));
        }

        if (primeiraData == null || segundaData == null) {
            return ResponseEntity.ok(lancamentoService.consultarLancamentosPorContaCorrenteAteData(contaCorrente, primeiraData == null ? segundaData : primeiraData));
        }

        int comparacao = primeiraData.compareTo(segundaData);
        if (comparacao == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lancamentoService.consultarLancamentosPorContaCorrenteEmPeriodo(
            contaCorrente,
            comparacao < 0 ? primeiraData : segundaData,
            comparacao < 0 ? segundaData : primeiraData));
    }

    private @NotNull ContaCorrente criarContaCorrenteComId(Long contaCorrenteId) {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(contaCorrenteId);
        return contaCorrente;
    }

}
