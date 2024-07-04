package dev.processo_seletivo.gerenciador_ativos.controller;

import dev.processo_seletivo.gerenciador_ativos.model.Posicao;
import dev.processo_seletivo.gerenciador_ativos.service.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v0/contas-corrente")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @GetMapping("/{id}/saldo/{data}")
    public ResponseEntity<BigDecimal> consultarSaldoContaCorrente(@PathVariable("id") Long contaCorrenteId, @PathVariable("data") String dataPosicao) {
        return ResponseEntity.ok(contaCorrenteService.consultarSaldoContaCorrente(contaCorrenteId, LocalDateTime.parse(dataPosicao, DateTimeFormatter.ISO_DATE_TIME)));
    }

    @GetMapping("{id}/posicoes/{data}")
    public ResponseEntity<List<Posicao>> consultarPosicoesContaCorrente(@PathVariable("id") Long contaCorrenteId, @PathVariable("data") String dataPosicao) {
        return ResponseEntity.ok(contaCorrenteService.consultarPosicoesContaCorrente(contaCorrenteId, LocalDateTime.parse(dataPosicao, DateTimeFormatter.ISO_DATE_TIME)));
    }

}
