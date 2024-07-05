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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/{conta-id}")
    public ResponseEntity<List<Lancamento>> consultarLancamentosPorContaCorrentePorData(
        @PathVariable("conta-id") Long contaCorrenteId,
        @RequestParam(value = "data-i") String primeiraDataTexto,
        @RequestParam(value = "data-f") String segundaDataTexto) {

        LocalDateTime primeiraData = LocalDateTime.parse(primeiraDataTexto, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime segundaData = LocalDateTime.parse(segundaDataTexto, DateTimeFormatter.ISO_DATE_TIME);
        ContaCorrente contaCorrente = criarContaCorrenteComId(contaCorrenteId);


        int comparacao = primeiraData.compareTo(segundaData);
        if (comparacao == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(lancamentoService.consultarLancamentosPorContaCorrenteEmPeriodo(
            contaCorrente,
            primeiraData,
            segundaData));
    }

    private @NotNull ContaCorrente criarContaCorrenteComId(Long contaCorrenteId) {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setId(contaCorrenteId);
        return contaCorrente;
    }

}
