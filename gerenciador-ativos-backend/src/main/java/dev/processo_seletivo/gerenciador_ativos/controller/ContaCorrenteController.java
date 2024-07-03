package dev.processo_seletivo.gerenciador_ativos.controller;

import dev.processo_seletivo.gerenciador_ativos.entity.ContaCorrente;
import dev.processo_seletivo.gerenciador_ativos.model.Posicao;
import dev.processo_seletivo.gerenciador_ativos.service.ContaCorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v0/contas-corrente")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @PostMapping
    public ResponseEntity<ContaCorrente> incluirContaCorrente() {
        return ResponseEntity.ok(contaCorrenteService.incluirContaCorrente());
    }

    @GetMapping
    public ResponseEntity<List<ContaCorrente>> consultarContasCorrente() {
        return ResponseEntity.ok(contaCorrenteService.consultarContasCorrente());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrente> consultarContaCorrentePorId(@PathVariable("id") Long contaCorrenteId) {
        Optional<ContaCorrente> contaCorrenteOptional = contaCorrenteService.consultarContaCorrentePorId(contaCorrenteId);
        return contaCorrenteOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removerContaCorrentePorId(@PathVariable("id") Long contaCorrenteId) {
        return ResponseEntity.ok(contaCorrenteService.removerContaCorrentePorId(contaCorrenteId));
    }

    @GetMapping("/{id}/saldo/{data}")
    public ResponseEntity<BigDecimal> consultarSaldoContaCorrente(@PathVariable("id") Long contaCorrenteId, @PathVariable("data") LocalDateTime dataPosicao) {
        return ResponseEntity.ok(contaCorrenteService.consultarSaldoContaCorrente(contaCorrenteId, dataPosicao));
    }

    @GetMapping("{id}/posicoes/{data}")
    public ResponseEntity<List<Posicao>> consultarPosicoesContaCorrente(@PathVariable("id") Long contaCorrenteId, @PathVariable("data") LocalDateTime dataPosicao) {
        return ResponseEntity.ok(contaCorrenteService.consultarPosicoesContaCorrente(contaCorrenteId, dataPosicao));
    }

}
